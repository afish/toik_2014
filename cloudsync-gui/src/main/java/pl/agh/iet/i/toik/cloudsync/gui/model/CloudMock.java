/**
 * 
 */
package pl.agh.iet.i.toik.cloudsync.gui.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

/**
 * @author daniel
 *
 */
@Component
public class CloudMock implements Cloud {
	
	private static Logger logger = LoggerFactory.getLogger(CloudMock.class);
	
	
	CloudInformation cloudInformation;
	
	public CloudMock() {
	}
	
	public CloudMock(String id, String name) {
		logger.info("Creating mock cloud: " + name);
		logger.info(this.toString());
		cloudInformation = new CloudInformation(id, name, this);
	}
	
	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#getCloudInformation()
	 */
	@Override
	public CloudInformation getCloudInformation() {
		logger.info("Get cloud information:" + cloudInformation.toString());
		return cloudInformation;
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#login(pl.agh.iet.i.toik.cloudsync.logic.Account)
	 */
	@Override
	public String login(Account account) {
		logger.info("Login: " + account.toString());
		return UUID.randomUUID().toString();
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#logout(java.lang.String)
	 */
	@Override
	public void logout(String sessionId) {
		logger.info("Logout: " + cloudInformation.toString());
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#listAllFiles(java.lang.String, pl.agh.iet.i.toik.cloudsync.logic.CloudFile)
	 */
	@Override
	public CloudTask<List<CloudFile>> listAllFiles(String sessionId,
			CloudFile directory) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#download(java.lang.String, pl.agh.iet.i.toik.cloudsync.logic.CloudFile, java.io.OutputStream)
	 */
	@Override
	public CloudTask<Boolean> download(String sessionId, CloudFile file,
			OutputStream outputStream) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#upload(java.lang.String, pl.agh.iet.i.toik.cloudsync.logic.CloudFile, java.lang.String, java.io.InputStream, java.lang.Long)
	 */
	@Override
	public CloudTask<CloudFile> upload(String sessionId, CloudFile directory,
			String fileName, InputStream fileInputStream, Long fileSize) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.Cloud#remove(java.lang.String, pl.agh.iet.i.toik.cloudsync.logic.CloudFile)
	 */
	@Override
	public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
