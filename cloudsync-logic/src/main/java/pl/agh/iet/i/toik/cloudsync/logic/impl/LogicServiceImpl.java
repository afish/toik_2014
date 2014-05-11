package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.agh.iet.i.toik.cloudsync.logic.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

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
    public CloudTask<Boolean> move(CloudSession sessionFrom, String fromFileName,
                                   CloudSession sessionTo, String toFileName, final Callable<Boolean> callback) {
        logger.info("Moving: " + fromFileName + " -> " + toFileName);

        final CloudTask<Boolean> copyTask = copy(sessionFrom, fromFileName, sessionTo, toFileName, callback);
        final CloudTask<Boolean> removeTask = sessionFrom.getCloudInformation().getCloud().remove(sessionFrom.getSessionId(), fromFileName, callback);
        CloudTask<Boolean> mergedTask = new JoinedCloudTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if(copyTask.get()){
                    executor.execute(removeTask);
                    return removeTask.get();
                }

                return false;
            }
        }, copyTask, removeTask);

        executor.execute(mergedTask);

        return mergedTask;
    }

    @Override
    public CloudTask<Boolean> copy(CloudSession session, String srcFileName, CloudSession sessionTo, String destFileName,
                                   Callable<Boolean> callback) {
        logger.info("Copying: " + srcFileName + " -> " + destFileName);

        PipedOutputStream outputStream = new PipedOutputStream();
        final CloudTask<Boolean> firstTask = session.getCloudInformation().getCloud().download(session.getSessionId(), srcFileName, outputStream, callback);
        PipedInputStream inputStream = null;
        try {
            inputStream = new PipedInputStream(outputStream);
        } catch (IOException e) {
            // How to recover?
            e.printStackTrace();
        }
        final CloudTask<Boolean> secondTask = sessionTo.getCloudInformation().getCloud().upload(sessionTo.getSessionId(), destFileName, inputStream, callback);

        CloudTask<Boolean> mergedTask = new JoinedCloudTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return firstTask.get() && secondTask.get();
            }
        }, firstTask, secondTask);

        executor.execute(firstTask);
        executor.execute(secondTask);
        executor.execute(mergedTask);

        return mergedTask;
    }

    @Override
    public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, String directory,
                                                Callable<List<CloudFile>> callback) {
        logger.info("Listing all files: " + directory);

        CloudTask<List<CloudFile>> listCloudTask = sessionFrom.getCloudInformation().getCloud().listAllFiles(sessionFrom.getSessionId(), directory, callback);
        executor.execute(listCloudTask);

        return listCloudTask;
    }

    @Override
    public CloudTask<Boolean> delete(CloudSession session, String fileName,
                                     Callable<Boolean> callback) {
        logger.info("Deleting: " + fileName);

        CloudTask<Boolean> remove = session.getCloudInformation().getCloud().remove(session.getSessionId(), fileName, callback);
        executor.execute(remove);

        return remove;
    }

    private class JoinedCloudTask<T> extends CloudTask<T>{

        private CloudTask<T> first, second;

        public JoinedCloudTask(Callable<T> callback, CloudTask<T> first, CloudTask<T> second){
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
