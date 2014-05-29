package pl.agh.iet.i.toik.cloudsync.googledrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ParentList;
import com.google.api.services.drive.model.ParentReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import pl.agh.iet.i.toik.cloudsync.googledrive.task.GoogleDriveCloudCallable;
import pl.agh.iet.i.toik.cloudsync.googledrive.task.GoogleDriveCloudTask;
import pl.agh.iet.i.toik.cloudsync.logic.*;
import pl.agh.iet.i.toik.cloudsync.logic.CloudType;

import java.io.*;
import java.util.*;

public class GoogleDriveCloud implements Cloud {

    private static Logger logger = LoggerFactory.getLogger(GoogleDriveCloud.class);

	@Value("${googleDriveID}")
	private String GOOGLE_DRIVE_ID;

	@Value("${googleDriveName}")
	private String GOOGLE_DRIVE_NAME;

	@Value("${clientId}")
	private String CLIENT_ID;

	@Value("${clientSecret}")
	private String CLIENT_SECRET;

	private final Map<String, Account> SESSION = new HashMap<>();

	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	@Autowired
	private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

	@Autowired
	private JsonFactory jsonFactory;

	@Autowired
	private HttpTransport httpTransport;

    @Override
    public CloudInformation getCloudInformation() {
        return new CloudInformation(GOOGLE_DRIVE_ID,  GOOGLE_DRIVE_NAME, this, CloudType.GOOGLEDRIVE);
    }

    @Override
    public String login(Account account) {
	    String code = (String) account.getPropertyList().get("cloud.google.code");
	    logger.debug("Login for "+account.getName());
        if(code != null && !code.isEmpty()){
            try {
                GoogleTokenResponse response = googleAuthorizationCodeFlow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
                GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
                account.getPropertyList().put("cloud.google.token", credential.getAccessToken());
                account.getPropertyList().put("cloud.google.token.refresh", credential.getAccessToken());
                String sessionID = UUID.randomUUID().toString();
                SESSION.put(sessionID, account);
	            logger.debug("Session ID for "+account.getName()+" is "+sessionID);
                return sessionID;
            } catch (IOException e) {
                logger.error("Problem with login", e.getMessage());
                return null;
            }
        }else{
            logger.warn("Code is empty or null");
            return null;
        }
    }

    @Override
    public void logout(String sessionId) {
        Account account = SESSION.get(sessionId);
        if(account != null){
            account.getPropertyList().remove("cloud.google.code");
            account.getPropertyList().remove("cloud.google.token");
            account.getPropertyList().remove("cloud.google.token.refresh");
            SESSION.remove(sessionId);
        }else{
            logger.warn("Session id not found");
        }
    }

    @Override
    public CloudTask<List<CloudFile>> listAllFiles(final String sessionId, final CloudFile directory) {
        GoogleDriveCloudCallable<List<CloudFile>> callable = new GoogleDriveCloudCallable<List<CloudFile>>() {
            @Override
            public List<CloudFile> call() throws Exception {
	            logger.debug("List All files for: "+sessionId);
                this.setProgress(0.0f);
	            Account account = SESSION.get(sessionId);
                if(account == null){
                    logger.warn("Session id doesn't exists");
                    return null;
                }
                List<CloudFile> fileList = new ArrayList<>();
                Drive drive = getDrive((String)account.getPropertyList().get("cloud.google.token"), (String)account.getPropertyList().get("cloud.google.token.refresh"));
                String folderId;
                if(directory == null) {
                    folderId = "root";
                } else {
                    folderId = directory.getId();
                }
                try {
                    Drive.Children.List request = drive.children().list(folderId);
	                this.setProgress(0.2f);
	                do {
                        ChildList children = request.execute();
                        for(ChildReference child : children.getItems()) {
                            com.google.api.services.drive.model.File file = drive.files().get(child.getId()).execute();
                            boolean isDir = file.getMimeType().equals("application/vnd.google-apps.folder");
	                        ParentList parents = drive.parents().list(file.getId()).execute();
	                        String fullPath = "/";
	                        for (ParentReference parent : parents.getItems()) {
								if(!parent.getIsRoot()) {
									com.google.api.services.drive.model.File file2 = drive.files().get(parent.getId()).execute();
									fullPath += file2.getTitle()+"/";
								}
	                        }
	                        fullPath += file.getTitle();

                            long size = file.getFileSize() == null ? -1 : file.getFileSize();
                            CloudFile cloudFile = new CloudFile(file.getTitle(), new Date(file.getCreatedDate().getValue()), isDir, fullPath, file.getId(), size);
                            fileList.add(cloudFile);
                            logger.info(cloudFile+" Size "+cloudFile.getSize());
                        }
                        request.setPageToken(children.getNextPageToken());
                    } while (request.getPageToken() != null &&
                            request.getPageToken().length() > 0);

                } catch (IOException e) {
                    logger.error("An error occurred: ", e.getMessage());
                    return null;
                }
	            this.setProgress(1.0f);
                return fileList;
            }
        };

        return new GoogleDriveCloudTask<>(callable);

    }

    @Override
    public CloudTask<Boolean> download(final String sessionId, final CloudFile cloudFile, final OutputStream outputStream) {
    	GoogleDriveCloudCallable<Boolean> callable = new GoogleDriveCloudCallable<Boolean>() {
    		 @Override
             public Boolean call() throws Exception {
			    this.setProgress(0.0f);
			    logger.debug("Download file for: "+sessionId);
			    Account account = SESSION.get(sessionId);
				if(account == null){
				    logger.warn("Session id doesn't exists");
				     return null;
			    }
			    Drive drive = getDrive((String)account.getPropertyList().get("cloud.google.token"), (String)account.getPropertyList().get("cloud.google.token.refresh"));
			     this.setProgress(0.2f);
			     try {
				    com.google.api.services.drive.model.File file = drive.files().get(cloudFile.getId()).execute();
				    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
					    HttpResponse resp = drive.getRequestFactory()
							    .buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
					    copyStream(resp.getContent(), outputStream);
				    }
					this.setProgress(1.0f);
				    return true;
			    } catch (IOException e) {
				    logger.error("Error while downloading file: " +e.getMessage());
				    return false;
			    }
    		 }
    	};
    	return new GoogleDriveCloudTask<>(callable);
    }

    @Override
    public CloudTask<CloudFile> upload(final String sessionId, final CloudFile directory, final String fileName,
                                       final InputStream fileInputStream, final Long fileSize) {
    	GoogleDriveCloudCallable<CloudFile> callable = new GoogleDriveCloudCallable<CloudFile>() {
    		@Override
    		public CloudFile call() throws Exception {
			    this.setProgress(0.0f);
			    logger.debug("Upload file for: "+sessionId);
			    Account account = SESSION.get(sessionId);
			    if(account == null){
				    logger.warn("Session id doesn't exists");
				    return null;
			    }
			    Drive drive = getDrive((String)account.getPropertyList().get("cloud.google.token"), (String)account.getPropertyList().get("cloud.google.token.refresh"));
			    this.setProgress(0.2f);
			    com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
			    body.setTitle(fileName);
			    body.setFileSize(fileSize);
			    String parentId;
				if(directory != null) {
					parentId = directory.getId();
				} else {
					parentId = "root";
				}
			    body.setParents(Arrays.asList(new ParentReference().setId(parentId)));
		
			    InputStreamContent inputStreamContent = new InputStreamContent(null, fileInputStream);
			    try {
				    com.google.api.services.drive.model.File file = drive.files().insert(body, inputStreamContent).execute();
				    this.setProgress(0.8f);
				    boolean isDir = file.getMimeType().equals("application/vnd.google-apps.folder");
				    ParentList parents = drive.parents().list(file.getId()).execute();
				    String fullPath = "/";
				    for (ParentReference parent : parents.getItems()) {
					    if(!parent.getIsRoot()) {
						    com.google.api.services.drive.model.File file2 = drive.files().get(parent.getId()).execute();
						    fullPath += file2.getTitle()+"/";
					    }
				    }
				    fullPath += file.getTitle();
				    long size = file.getFileSize() == null ? -1 : file.getFileSize();
				    CloudFile cloudFile = new CloudFile(file.getTitle(), new Date(file.getCreatedDate().getValue()), isDir, fullPath, file.getId(), size);
				    logger.info(cloudFile+" Size "+cloudFile.getSize());
				    this.setProgress(1.0f);
					return cloudFile;
			    } catch (IOException e) {
				    logger.error("Error while uploading file: " + e.getMessage());
				    return null;
			    }
    		}
    	};
    	return new GoogleDriveCloudTask<>(callable);
    }

    @Override
    public CloudTask<Boolean> remove(final String sessionId, final CloudFile file) {
    	GoogleDriveCloudCallable<Boolean> callable = new GoogleDriveCloudCallable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				this.setProgress(0.0f);
				logger.debug("remove file for: "+sessionId);
				Account account = SESSION.get(sessionId);
				if(account == null){
					logger.warn("Session id doesn't exists");
					return false;
				}
			    Drive drive = getDrive((String)account.getPropertyList().get("cloud.google.token"), (String)account.getPropertyList().get("cloud.google.token.refresh"));
				this.setProgress(0.2f);
				try {
				    drive.files().delete(file.getId()).execute();
				    this.setProgress(1.0f);
				    return true;
			    } catch (IOException e) {
				    logger.error("Error while removing file: " +e.getMessage());
				    return false;
			    }
			}
		};
    	return new GoogleDriveCloudTask<>(callable);
    }

	private Drive getDrive(String token, String refreshToken) {
		final GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(jsonFactory).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
		credential.setAccessToken(token);
		credential.setRefreshToken(refreshToken);
		final HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
		return new Drive(httpTransport, jsonFactory, requestFactory.getInitializer());
	}

	private void copyStream(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

}
