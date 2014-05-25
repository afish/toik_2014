package pl.agh.iet.i.toik.cloudsync.dropbox;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

@Component("dropboxCloud")
public class DropboxCloud implements Cloud {

    @Override
    public CloudInformation getCloudInformation() {
        return new CloudInformation("dropbox-cloud", "CloudSync - Dropbox", this);
    }

    @Override
    public String login(Account account) {
    	//TODO implement me
        return null;
    }

    @Override
    public void logout(String sessionId) {
    	//TODO implement me
    }

    @Override
    public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
    	//TODO implement me
        return null;
    }

    @Override
    public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
    	//TODO implement me
        return null;
    }

    @Override
    public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
    	//TODO implement me
        return null;
    }

    @Override
    public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
    	//TODO implement me
        return null;
    }
}
