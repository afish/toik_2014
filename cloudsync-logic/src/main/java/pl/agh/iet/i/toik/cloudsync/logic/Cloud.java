package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Interface to be implemented by cloud providers.
 */
public interface Cloud {
	/**
	 * Returns {@link CloudInformation} of this cloud.
	 */
	CloudInformation getCloudInformation();

	/**
	 * Logs in. Oauth tokens should be get from account's property list. Returns session id which
	 * identifies the connection. The session id is passed to the methods manipulating files in the
	 * cloud.
	 */
	String login(Account account);

	/**
	 * Logs out, after successful invocation sessionId is no longer valid.
	 */
	void logout(String sessionId);

	/**
	 * Lists all files under the given directory. CloudTask.get should return the list of files. The
	 * caller of this method will call CloudTask.run.
	 */
	CloudTask<List<CloudFile>> listAllFiles(String sessionId, String directory);

	/**
	 * Downloads file with the given name. CloudTask.get should return boolean stating whether
	 * operation succeeded. The caller of this method will call CloudTask.run.
	 * 
	 * File contents is written to the outputStream;
	 */
	CloudTask<Boolean> download(String sessionId, String absoluteFileName, OutputStream outputStream);

	/**
	 * Uploads file with the given name. Gets data from the {@link InputStream}. CloudTask.get
	 * should return boolean stating whether operation succeeded. The caller of this method will
	 * call CloudTask.run.
	 */
	CloudTask<Boolean> upload(String sessionId, String absoluteFileName, InputStream fileInputStream);

	/**
	 * Removes file with the given name. CloudTask.get should return boolean stating whether
	 * operation succeeded. The caller of this method will call CloudTask.run.
	 */
	CloudTask<Boolean> remove(String sessionId, String absoluteFileName);
}
