package pl.agh.iet.i.toik.cloudsync.dropbox.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.DownloadTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.UploadTaskFactory;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.dropbox.core.DbxClient;

public class DropboxService {

	@Autowired
	private DropboxConfiguration configuration;

	@Autowired
	private DownloadTaskFactory downloadTaskFactory;
	
	@Autowired 
	private UploadTaskFactory uploadTaskFactory;
	
	@Autowired
	private AuthService authService;

	public String login(Account account){
		String sessionId = authService.login(configuration, account);
		return sessionId;
	}

	public void logout(String sessionId) {
		authService.logout(sessionId);
	}

	public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
		//TODO: implement me
		return null;
	}

	public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
		DbxClient dbxClient = authService.getDbxClient(sessionId);
		CloudTask<Boolean> downloadTask = this.downloadTaskFactory.create(dbxClient, outputStream, file.getFullPath());
		return downloadTask;
	}

	public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
		DbxClient dbxClient = authService.getDbxClient(sessionId);
		CloudTask<CloudFile> uploadTask = this.uploadTaskFactory.create(dbxClient, directory, fileName, fileInputStream, fileSize);
		return uploadTask;
	}

	public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
		//TODO: implement me
		return null;
	}

}
