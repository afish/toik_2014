package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Interface to be used to manipulate files in clouds.
 */
public interface LogicService {

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
	public CloudTask<Boolean> copy(CloudSession session, String srcFileName, CloudSession sessionTo,
                                   String destFileName,	Callable<Boolean> callback);

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
	public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, String directory,
			Callable<List<CloudFile>> callback);
}