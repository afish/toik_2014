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
	private DbxClient client;

	private String currentPath;
	private String accessToken;

	public String login(Account account){
		//DbxRequestConfig dbxConfig = configuration.getConfig();
		//if(account == null || account.getPropertyList().get(""))
		//TODO: implement me
		return null;
	}

	public void logout(String sessionId) {
		//TODO: implement me
	}

	public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
		//TODO: implement me
		return null;
	}

	public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
		//TODO: sessionId
		CloudTask<Boolean> downloadTask = this.downloadTaskFactory.create(client, outputStream, file.getFullPath());
		return downloadTask;
	}

	public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
		//TODO: sessionId
		CloudTask<CloudFile> uploadTask = this.uploadTaskFactory.create(client, directory, fileName, fileInputStream, fileSize);
		return uploadTask;
	}

	public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
		//TODO: implement me
		return null;
	}

}
