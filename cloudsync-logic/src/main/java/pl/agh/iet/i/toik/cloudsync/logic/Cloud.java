package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Interface to be implemented by cloud providers.
 */
public interface Cloud {
	/** Globally unique identifier. */
	String getId();

	/** Human readable name. */
	String getName();

	/**
	 * Logs in. Returns session id which identifies the connection. The session id is passed to the
	 * methods manipulating files in the cloud.
	 */
	String login(Account account);

	/** Logs out, after successful invocation sessionId is no longer valid. */
	void logout(String sessionId);

	/** Lists all files available in the cloud. CloudTask is called with the list of files. */
	CloudTask<List<CloudFile>> listAllFiles(String sessionId, Callable<List<CloudFile>> callback);

	/** Downloads file with the given name. CloudTask is called with the file name. */
	CloudTask<String> download(String sessionId, String absolutFileName, Callable<String> callback);

	/**
	 * Uploads file with the given name. Gets data from the {@link InputStream}. CloudTask is called
	 * with the file name.
	 */
	CloudTask<String> upload(String sessionId, String absolutFileName, InputStream fileInputStream,
			Callable<String> callback);

	/** Removes file with the given name. CloudTask is called with the file name. */
	CloudTask<String> remove(String sessionId, String absolutFileName, Callable<String> callback);
}
