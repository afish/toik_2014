package pl.agh.iet.i.toik.cloudsync.googledrive;

import org.springframework.stereotype.Service;
import pl.agh.iet.i.toik.cloudsync.logic.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class GoogleDriveCloud implements Cloud {

    @Override
    public CloudInformation getCloudInformation() {
        return null;
    }

    @Override
    public String login(Account account) {
        return null;
    }

    @Override
    public void logout(String sessionId) {

    }

    @Override
    public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
        return null;
    }

    @Override
    public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
        return null;
    }

    @Override
    public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName,
                                       InputStream fileInputStream, Long fileSize) {
        return null;
    }

    @Override
    public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
        return null;
    }
}
