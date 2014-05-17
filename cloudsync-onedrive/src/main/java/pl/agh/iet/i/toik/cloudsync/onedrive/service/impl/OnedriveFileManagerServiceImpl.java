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
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressCallable;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressTask;
import pl.agh.iet.i.toik.cloudsync.onedrive.util.JSONResolver;

import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnedriveFileManagerServiceImpl implements OnedriveFileManagerService {
    private static Logger logger = LoggerFactory.getLogger(OnedriveFileManagerServiceImpl.class);

    @Autowired
    private OnedriveAccountService onedriveAccountService;

    @Autowired
    private Client client;

    @Autowired
    private JSONResolver jsonResolver;

    @Override
    public ProgressTask<Boolean> download(final String sessionId, final CloudFile file, final OutputStream outputStream) {
        ProgressCallable<Boolean> callable = new ProgressCallable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                logger.debug("Preparing download file \"{}\", {}", file.getName(), file);

                if (file.isDirectory()) {
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
                int totalReadBytes = 0;
                int maxSize = response.getLength();
                logger.debug("Downloading file \"{}\" with size={}B, {}", file.getName(), maxSize, file);

                try {
                    BufferedInputStream is = new BufferedInputStream(response.getEntityInputStream());
                    while ((readBytes = is.read(buffer, 0, 65536)) > 0) {
                        totalReadBytes += readBytes;
                        setProgress((float) totalReadBytes / maxSize);
                        outputStream.write(buffer, 0, readBytes);
                    }
                } catch (IOException e) {
                    logger.error("Error occurred during downloading file from remove service", e);
                    return false;
                }
                logger.info("Download file {} completed, received {}B", file, totalReadBytes);
                return true;
            }
        };
        return new ProgressTask<>(callable);
    }

    @Override
    public ProgressTask<Boolean> remove(final String sessionId, final CloudFile file) {
        ProgressCallable<Boolean> callable = new ProgressCallable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String fileType = file.isDirectory() ? "file" : "directory";
                logger.debug("Preparing to remove {} \"{}\", {}", fileType, file.getName(), file);

                String accessToken = onedriveAccountService.getAccessToken(sessionId);
                if (accessToken == null) {
                    logger.warn("Access token is null");
                    return false;
                }

                WebResource webResource = client
                        .resource("https://apis.live.net/v5.0/" + file.getId())
                        .queryParam("access_token", accessToken);

                ClientResponse response = webResource.delete(ClientResponse.class);

                this.setProgress(1.0f);

                if (response.getStatus() == 204) {
                    logger.info("{} {} successfully removed from OneDrive", fileType, file.getName());
                    return true;
                } else {
                    logger.info("Error while removing {} : {}. Remote service returned HTTP error code: {}", fileType, file.getName(), response.getStatus());
                    return false;
                }
            }
        };
        return new ProgressTask<>(callable);
    }

    @Override
    public ProgressTask<List<CloudFile>> listFiles(final String sessionId, final CloudFile directory) {
        ProgressCallable<List<CloudFile>> callable = new ProgressCallable<List<CloudFile>>() {
            @Override
            public List<CloudFile> call() throws Exception {
                List<CloudFile> filesList;

                if (!directory.isDirectory()) {
                    logger.warn("Specified file is not a directory: {}", directory.getName());
                    return new ArrayList<>();
                }

                String accessToken = onedriveAccountService.getAccessToken(sessionId);
                if (accessToken == null) {
                    logger.warn("Access token is null");
                    return new ArrayList<>();
                }

                WebResource webResource = client
                        .resource("https://apis.live.net/v5.0/" + directory.getId() + "/files")
                        .queryParam("access_token", accessToken);

                ClientResponse response = webResource
                        .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

                if (response.getStatus() != 200) {
                    logger.error("Remote service returned HTTP error code: {} while listing files in {}", response.getStatus(), directory.getName());
                    return new ArrayList<>();
                }

                //TODO add parsing progress if really needed
                filesList = jsonResolver.resolveFileListDetails(response.getEntity(String.class), directory);
                logger.info("Files list successfully loaded from directory {}", directory.getName());
                setProgress(1.0f);

                return filesList;
            }
        };
        return new ProgressTask<>(callable);
    }

    @Override
    public ProgressTask<CloudFile> upload(final String sessionId, final CloudFile directory, final String fileName, final InputStream fileInputStream) {
        ProgressCallable<CloudFile> callable = new ProgressCallable<CloudFile>() {
            @Override
            public CloudFile call() throws Exception {

                String accessToken = onedriveAccountService.getAccessToken(sessionId);
                if (accessToken == null) {
                    logger.warn("Access token is null");
                    return null;
                }

                URL url = new URL("https://apis.live.net/v5.0/" + directory.getId() + "/files/" + fileName + "?access_token=" + accessToken);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("PUT");

                byte[] buffer = new byte[65536];
                int readBytes;

                // Cannot obtain file length from inputstream which means that
                // we can't create working progress without that detail provided (probably)
                try {
                    OutputStream out = httpURLConnection.getOutputStream();
                    while ((readBytes = fileInputStream.read(buffer, 0, 65536)) > 0) {
                        out.write(buffer, 0, readBytes);
                    }
                    out.close();
                } catch (IOException e) {
                    logger.error("Error occurred during uploading file to server", e);
                    return null;
                }

                if (httpURLConnection.getResponseCode() != 200) {
                    logger.error("Remote service returned HTTP error code: {} while uploading file to server",
                            httpURLConnection.getResponseCode(), fileName);
                    return null;
                }

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(
                        httpURLConnection.getInputStream()));
                String line;
                StringBuilder serverResponse = new StringBuilder();
                while ((line = responseReader.readLine()) != null) {
                    serverResponse.append(line);
                }
                responseReader.close();

                CloudFile cloudFile = getUploadedFileDetails(serverResponse.toString(), accessToken, directory);
                setProgress(1.0f);
                return cloudFile;
            }
        };
        return new ProgressTask<>(callable);
    }

    private CloudFile getUploadedFileDetails(String serverResponse, String accessToken, CloudFile parent) {
        String fileId = jsonResolver.resolveUploadedFileId(serverResponse);

        WebResource webResource = client
                .resource("https://apis.live.net/v5.0/" + fileId)
                .queryParam("access_token", accessToken);

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            logger.error("Remote service returned HTTP error code: {} while obtaining file details {}", response.getStatus(), fileId);
            return null;
        }

        return jsonResolver.resolveUploadedFileDetails(response.getEntity(String.class), parent);
    }

}
