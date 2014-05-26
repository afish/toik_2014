package pl.agh.iet.i.toik.cloudsync.onedrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.iet.i.toik.cloudsync.logic.*;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveFileManagerService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component//("onedriveCloud")
public class OnedriveCloud implements Cloud {

    @Autowired
    private OnedriveAccountService onedriveAccountService;

    @Autowired
    private OnedriveFileManagerService onedriveFileManagerService;

    @Override
    public CloudInformation getCloudInformation() {
        return new CloudInformation("onedrive-cloud", "CloudSync - Onedrive", this);
    }

    @Override
    public String login(Account account) {
        return onedriveAccountService.login(account);
    }

    @Override
    public void logout(String sessionId) {
        onedriveAccountService.logout(sessionId);
    }

    @Override
    public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
        return onedriveFileManagerService.listFiles(sessionId, directory);
    }

    @Override
    public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
        return onedriveFileManagerService.download(sessionId, file, outputStream);
    }

    @Override
    public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
        return onedriveFileManagerService.upload(sessionId, directory, fileName, fileInputStream, fileSize);
    }

    @Override
    public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
        return onedriveFileManagerService.remove(sessionId, file);
    }
}
