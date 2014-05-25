package pl.agh.iet.i.toik.cloudsync.googledrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.agh.iet.i.toik.cloudsync.logic.*;

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
        return new CloudInformation(GOOGLE_DRIVE_ID,  GOOGLE_DRIVE_NAME, this);
    }

    @Override
    public String login(Account account) {
	    String code = (String) account.getPropertyList().get("cloud.google.code");
        if(code != null || !code.isEmpty()){
            try {
                GoogleTokenResponse response = googleAuthorizationCodeFlow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
                GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
                account.getPropertyList().put("cloud.google.token", credential.getAccessToken());
                account.getPropertyList().put("cloud.google.token.refresh", credential.getAccessToken());
                String sessionID = UUID.randomUUID().toString();
                SESSION.put(sessionID, account);
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
    public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
        Account account = SESSION.get(sessionId);
	    Drive drive = getDrive((String)account.getPropertyList().get("cloud.google.token"), (String)account.getPropertyList().get("cloud.google.token.refresh"));
	    String folderId;
	    if(directory == null) {
		    folderId = "root";
	    } else {
		    folderId = directory.getId();
	    }
	    try {
		    Drive.Children.List request = drive.children().list(folderId);
		    do {
			    ChildList children = request.execute();
			    for(ChildReference child : children.getItems()) {
				    com.google.api.services.drive.model.File file = drive.files().get(child.getId()).execute();
				    boolean isDir = file.getMimeType().equals("application/vnd.google-apps.folder");
				    String fullPath = "needToBeImplemented";
				    long size = file.getFileSize() == null ? -1 : file.getFileSize();
				    CloudFile cloudFile = new CloudFile(file.getTitle(), new Date(file.getCreatedDate().getValue()), isDir, fullPath, file.getId(), size);
				    System.out.println(cloudFile+" Size "+cloudFile.getSize());
				    if(isDir) { /* Remove it */
					    listAllFiles(sessionId, cloudFile);
				    }
			    }
			    request.setPageToken(children.getNextPageToken());
		    } while (request.getPageToken() != null &&
				    request.getPageToken().length() > 0);

	    } catch (IOException e) {
		    System.out.println("An error occurred: " + e);
	    }

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

	private Drive getDrive(String token, String refreshToken) {
		final GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(jsonFactory).setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();
		credential.setAccessToken(token);
		credential.setRefreshToken(refreshToken);
		final HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
		return new Drive(httpTransport, jsonFactory, requestFactory.getInitializer());
	}

}
