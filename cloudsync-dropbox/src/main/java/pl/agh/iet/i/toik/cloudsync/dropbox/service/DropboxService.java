package pl.agh.iet.i.toik.cloudsync.dropbox.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.DownloadTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.ListAllTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.RemoveTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.TaskFactories;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.UploadTaskFactory;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.dropbox.core.DbxClient;

@Service
public class DropboxService {

	@Autowired
	private DropboxConfiguration dropboxConfiguration;

	@Autowired
	private AuthService authService;
	
	public String login(Account account) {
		String sessionId = authService.login(dropboxConfiguration, account);
		return sessionId;
	}

	public void logout(String sessionId) {
		authService.logout(sessionId);
	}

	public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
		DbxClient dbxClient = authService.getSession(sessionId).getDbxClient();
		TaskFactories taskFactories = dropboxConfiguration.getTaskFactories();
		ListAllTaskFactory listAllTaskFactory = taskFactories.getListAllTaskFactory();
		CloudTask<List<CloudFile>> cloudTask = listAllTaskFactory.create(dbxClient, directory);
		return cloudTask;
	}

	public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
		DbxClient dbxClient = authService.getSession(sessionId).getDbxClient();
		TaskFactories taskFactories = dropboxConfiguration.getTaskFactories();
		DownloadTaskFactory downloadTaskFactory = taskFactories.getDownloadTaskFactory();
		CloudTask<Boolean> downloadTask = downloadTaskFactory.create(dbxClient, outputStream, file.getFullPath());
		return downloadTask;
	}

	public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
		DbxClient dbxClient = authService.getSession(sessionId).getDbxClient();
		TaskFactories taskFactories = dropboxConfiguration.getTaskFactories();
		UploadTaskFactory uploadTaskFactory = taskFactories.getUploadTaskFactory();
		CloudTask<CloudFile> uploadTask = uploadTaskFactory.create(dbxClient, directory, fileName, fileInputStream, fileSize);
		return uploadTask;
	}

	public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
		DbxClient dbxClient = authService.getSession(sessionId).getDbxClient();
		TaskFactories taskFactories = dropboxConfiguration.getTaskFactories();
		RemoveTaskFactory removeTaskFactory = taskFactories.getRemoveTaskFactory();
		CloudTask<Boolean> removeTask = removeTaskFactory.create(dbxClient, file);
		return removeTask;
	}
	
	public DropboxConfiguration getDropboxConfiguration() {
		return dropboxConfiguration;
	}

}
