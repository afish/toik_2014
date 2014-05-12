package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.List;

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
	 * Moves fromFileName to toFileName. File names are absolute paths. CloudTask.get returns
	 * boolean stating whether the operation succeeded. Result should be get from the CloudTask via
	 * polling.
	 */
	public CloudTask<Boolean> move(CloudSession sessionFrom, String fromFileName,
			CloudSession sessionTo, String toFileName);

	/**
	 * Copies scrFileName to destFileName. File names are absolute paths. CloudTask.get returns
	 * boolean stating whether the operation succeeded. Result should be get from the CloudTask via
	 * polling.
	 */
	public CloudTask<Boolean> copy(CloudSession session, String srcFileName,
			CloudSession sessionTo, String destFileName);

	/**
	 * Deletes the given file. File name is absolute path. CloudTask.get returns boolean stating
	 * whether the operation succeeded. Result should be get from the CloudTask via polling.
	 */
	public CloudTask<Boolean> delete(CloudSession session, String fileName);

	/**
	 * Lists all files in the given directory. Directory name is absolute path. CloudTask.get
	 * returns the list of files. Result should be get from the CloudTask via polling.
	 */
	public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, String directory);
}