package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

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
	 * Lists all files under the given directory. CloudTask is called with the list of files.
	 */
	CloudTask<List<CloudFile>> listAllFiles(String sessionId, String directory,
			Callable<List<CloudFile>> callback);

	/**
	 * Downloads file with the given name. CloudTask is called with boolean stating whether
	 * operation succeeded.
	 * 
	 * File contents is written to the outputStream;
	 */
	CloudTask<Boolean> download(String sessionId, String absoluteFileName,
			OutputStream outputStream, Callable<Boolean> callback);

	/**
	 * Uploads file with the given name. Gets data from the {@link InputStream}. CloudTask is called
	 * with boolean stating whether operation succeeded.
	 */
	CloudTask<Boolean> upload(String sessionId, String absoluteFileName,
			InputStream fileInputStream, Callable<Boolean> callback);

	/**
	 * Removes file with the given name. CloudTask is called with boolean stating whether operation
	 * succeeded.
	 */
	CloudTask<Boolean> remove(String sessionId, String absoluteFileName, Callable<Boolean> callback);
}
