package pl.agh.iet.i.toik.cloudsync.dropbox;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.dropbox.service.DropboxService;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

@Component("dropboxCloud")
public class DropboxCloud implements Cloud {

	@Autowired
	private DropboxService dropboxService;

	@Override
	public CloudInformation getCloudInformation() {
		return new CloudInformation("dropbox-cloud", "CloudSync - Dropbox", this);
	}

	@Override
	public String login(Account account) {
		return this.dropboxService.login(account);
	}

	@Override
	public void logout(String sessionId) {
		this.dropboxService.logout(sessionId);
	}

	@Override
	public CloudTask<List<CloudFile>> listAllFiles(String sessionId, CloudFile directory) {
		return this.dropboxService.listAllFiles(sessionId, directory);
	}

	@Override
	public CloudTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream) {
		return this.dropboxService.download(sessionId, file, outputStream);
	}

	@Override
	public CloudTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName,
			InputStream fileInputStream, Long fileSize) {
		return this.dropboxService.upload(sessionId, directory, fileName, fileInputStream, fileSize);
	}

	@Override
	public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
		return this.dropboxService.remove(sessionId, file);
	}
	
	public DropboxService getDropboxService() {
		return dropboxService;
	}

}
