package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Interface to be used to manipulate files in clouds.
 */
public interface LogicService {

	/** Returns a list of public information of all registered clouds. */
	public abstract List<CloudInformation> getAllClouds();

	/**
	 * Logs in to the cloud. Oauth tokens should be get from account's property list.
	 */
	public CloudSession login(CloudInformation cloudInformation, Account account);

	/** Logs out from the cloud. */
	public void logout(CloudSession sessionId);

	/**
	 * Moves fromFileName to toFileName. File names are absolute paths. Callback is called with
	 * boolean stating whether the operation succeeded.
	 */
	public CloudTask<Boolean> move(CloudSession sessionFrom, String fromFileName,
			CloudSession sessionTo, String toFileName, Callable<Boolean> callback);

	/**
	 * Copies scrFileName to destFileName. File names are absolute paths. Callback is called with
	 * boolean stating whether the operation succeeded.
	 */
	public CloudTask<Boolean> copy(CloudSession session, String srcFileName, String destFileName,
			Callable<Boolean> callback);

	/**
	 * Deletes the given file. File name is absolute path. Callback is called with boolean stating
	 * whether the operation succeeded.
	 */
	public CloudTask<Boolean> delete(CloudSession session, String fileName,
			Callable<Boolean> callback);

	/**
	 * Lists all files in the given directory. Directory name is absolute path. Callback is called
	 * with the list of files.
	 */
	public CloudTask<List<CloudFile>> listFiles(CloudSession session, String directory,
			Callable<List<CloudFile>> callback);

	/**
	 * Downloads file with the given name. CloudTask is called with boolean stating whether
	 * operation succeeded.
	 * 
	 * File contents is written to the outputStream;
	 */
	CloudTask<Boolean> download(CloudSession sessionId, String absoluteFileName,
			OutputStream outputStream, Callable<Boolean> callback);

	/**
	 * Uploads file with the given name. Gets data from the {@link InputStream}. CloudTask is called
	 * with boolean stating whether operation succeeded.
	 */
	CloudTask<Boolean> upload(CloudSession sessionId, String absoluteFileName,
			InputStream fileInputStream, Callable<Boolean> callback);
}