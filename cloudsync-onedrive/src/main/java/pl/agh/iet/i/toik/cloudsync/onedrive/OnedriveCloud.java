package pl.agh.iet.i.toik.cloudsync.onedrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.agh.iet.i.toik.cloudsync.logic.*;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

@Component("onedriveCloud")
public class OnedriveCloud implements Cloud {

    @Autowired
    private OnedriveAccountService onedriveAccountService;

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
    public CloudTask<List<CloudFile>> listAllFiles(String sessionId, String directory, Callable<List<CloudFile>> callback) {
        return null;
    }

    @Override
    public CloudTask<Boolean> download(String sessionId, String absoluteFileName, OutputStream outputStream, Callable<Boolean> callback) {
        return null;
    }

    @Override
    public CloudTask<Boolean> upload(String sessionId, String absoluteFileName, InputStream fileInputStream, Callable<Boolean> callback) {
        return null;
    }

    @Override
    public CloudTask<Boolean> remove(String sessionId, String absoluteFileName, Callable<Boolean> callback) {
        return null;
    }
}
