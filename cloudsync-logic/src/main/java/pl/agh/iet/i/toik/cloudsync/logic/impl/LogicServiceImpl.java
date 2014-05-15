package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

/**
 * Logic service implementation.
 */
@Service
public class LogicServiceImpl implements LogicService {
    private static Logger logger = LoggerFactory.getLogger(LogicServiceImpl.class);
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    @Override
    public CloudSession login(CloudInformation cloudInformation, Account account) {
        logger.info("Logging in: " + cloudInformation + ", " + account);

        return new CloudSession(cloudInformation, cloudInformation.getCloud().login(account));
    }

    @Override
    public void logout(CloudSession session) {
        logger.info("Logging out: " + session);
        session.getCloudInformation().getCloud().logout(session.getSessionId());
    }

    @Override
    public CloudTask<CloudFile> move(CloudSession sessionFrom, CloudFile fromFile, CloudSession sessionTo, CloudFile destinationDirectory, String destinationFileName) {
        logger.info("Moving: " + fromFile + " -> " + destinationDirectory + "\\" + destinationFileName);

        final CloudTask<CloudFile> copyTask = copy(sessionFrom, fromFile, sessionTo, destinationDirectory, destinationFileName);
        final CloudTask<Boolean> removeTask = sessionFrom.getCloudInformation().getCloud().remove(sessionFrom.getSessionId(), fromFile);
        CloudTask<CloudFile> mergedTask = new JoinedCloudTask<>(new Callable<CloudFile>() {
            @Override
            public CloudFile call() throws Exception {
                CloudFile result = copyTask.get();
                if(result != null){
                    executor.execute(removeTask);
                    removeTask.get();
                    return result;
                }

                return null;
            }
        }, copyTask, removeTask);

        executor.execute(mergedTask);

        return mergedTask;
    }

    @Override
    public CloudTask<CloudFile> copy(CloudSession session, CloudFile fromFile, CloudSession sessionTo, CloudFile destinationDirectory, String destinationFileName) {
        logger.info("Copying: " + fromFile + " -> " + destinationDirectory + "\\" + destinationFileName);

        PipedOutputStream outputStream = new PipedOutputStream();
        final CloudTask<Boolean> firstTask = session.getCloudInformation().getCloud().download(session.getSessionId(), fromFile, outputStream);
        PipedInputStream inputStream = null;
        try {
            inputStream = new PipedInputStream(outputStream);
        } catch (IOException e) {
            // How to recover?
            e.printStackTrace();
        }
        final CloudTask<CloudFile> secondTask = sessionTo.getCloudInformation().getCloud().upload(sessionTo.getSessionId(), destinationDirectory, destinationFileName, inputStream);

        CloudTask<CloudFile> mergedTask = new JoinedCloudTask<>(new Callable<CloudFile>() {
            @Override
            public CloudFile call() throws Exception {
                if(firstTask.get()){
                    return secondTask.get();
                }else{
                    return null;
                }
            }
        }, firstTask, secondTask);

        executor.execute(firstTask);
        executor.execute(secondTask);
        executor.execute(mergedTask);

        return mergedTask;
    }

    @Override
    public CloudTask<Boolean> delete(CloudSession session, CloudFile file) {
        logger.info("Deleting: " + file);

        CloudTask<Boolean> remove = session.getCloudInformation().getCloud().remove(session.getSessionId(), file);
        executor.execute(remove);

        return remove;
    }

    @Override
    public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, CloudFile directory) {
        logger.info("Listing all files: " + directory);

        CloudTask<List<CloudFile>> listCloudTask = sessionFrom.getCloudInformation().getCloud().listAllFiles(sessionFrom.getSessionId(), directory);
        executor.execute(listCloudTask);

        return listCloudTask;
    }

    private class JoinedCloudTask<T, U, W> extends CloudTask<W>{

        private CloudTask<T> first;
        private CloudTask<U> second;

        public JoinedCloudTask(Callable<W> callback, CloudTask<T> first, CloudTask<U> second){
            super(callback);
            this.first = first;
            this.second = second;
        }

        @Override
        public float getProgress() {
            if(!first.isDone()){
                return first.getProgress() / 2;
            }else{
                return 0.5f + second.getProgress() / 2;
            }
        }
    }
}
