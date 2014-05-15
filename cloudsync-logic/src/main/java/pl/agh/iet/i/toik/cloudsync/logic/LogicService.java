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
	 * Moves fromFileName to toFileName, destinationDirectory represents a directory. CloudTask.get returns
	 * CloudFile representing the created file or null in case of error. Result should be get from the CloudTask via
	 * polling.
	 */
	public CloudTask<CloudFile> move(CloudSession sessionFrom, CloudFile fromFile,
			CloudSession sessionTo, CloudFile destinationDirectory, String destinationFileName);

	/**
	 * Copies scrFileName to destFileName, destinationDirectory represents a directory. CloudTask.get returns
	 * CloudFile representing the created file or null in case of error. Result should be get from the CloudTask via
	 * polling.
	 */
	public CloudTask<CloudFile> copy(CloudSession session, CloudFile fromFile,
			CloudSession sessionTo, CloudFile destinationDirectory, String destinationFileName);

	/**
	 * Deletes the given file. CloudTask.get returns boolean stating
	 * whether the operation succeeded. Result should be get from the CloudTask via polling.
	 */
	public CloudTask<Boolean> delete(CloudSession session, CloudFile file);

	/**
	 * Lists all files in the given directory. CloudTask.get
	 * returns the list of files. Result should be get from the CloudTask via polling.
     * Passing null as directory means listing the root directory;
	 */
	public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, CloudFile directory);
}