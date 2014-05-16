package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveFileManagerService;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.DownloadCallable;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.DownloadTask;

import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class OnedriveFileManagerServiceImpl implements OnedriveFileManagerService {
    private static Logger logger = LoggerFactory.getLogger(OnedriveFileManagerServiceImpl.class);

    @Autowired
    private OnedriveAccountService onedriveAccountService;

    @Autowired
    private Client client;


    @Override
    public DownloadTask download(final String sessionId, final CloudFile file, final OutputStream outputStream) {
        DownloadCallable callable = new DownloadCallable() {
            @Override
            public Boolean call() throws Exception {
                logger.info("Preparing download file \"{}\", {}", file.getName(), file);

                if(file.isDirectory()) {
                    logger.warn("Unable to download directory");
                    return false;
                }

                String accessToken = onedriveAccountService.getAccessToken(sessionId);
                if (accessToken == null) {
                    logger.warn("Access token is null");
                    return false;
                }
                WebResource webResource = client
                        .resource("https://apis.live.net/v5.0/" + file.getId() + "/content")
                        .queryParam("access_token", accessToken);

                ClientResponse response = webResource
                        .accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

                if (response.getStatus() != 200) {
                    logger.error("Remote service returned HTTP error code: {}", response.getStatus());
                    return false;
                }

                byte[] buffer = new byte[65536];
                int readBytes;
                int maxSize = response.getLength();
                logger.info("Downloading file \"{}\" with size={}B, {}", file.getName(), maxSize, file);

                try {
                    BufferedInputStream is = new BufferedInputStream(response.getEntityInputStream());
                    while ((readBytes = is.read(buffer, 0, 65536)) > 0) {
                        setProgress(1 - is.available() / maxSize);
                        outputStream.write(buffer, 0, readBytes);
                    }
                } catch (IOException e) {
                    logger.error("Error occurred during downloading file from remove service", e);
                    return false;
                }
                return true;
            }
        };
        return new DownloadTask(callable);
    }
}
