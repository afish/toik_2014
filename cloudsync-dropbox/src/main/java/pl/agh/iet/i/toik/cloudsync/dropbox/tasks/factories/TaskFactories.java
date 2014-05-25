package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import org.springframework.beans.factory.annotation.Autowired;

public class TaskFactories {

	@Autowired
	private DownloadTaskFactory downloadTaskFactory;

	@Autowired
	private UploadTaskFactory uploadTaskFactory;

	@Autowired
	private RemoveTaskFactory removeTaskFactory;
	
	@Autowired
	private ListAllTaskFactory listAllTaskFactory;

	public DownloadTaskFactory getDownloadTaskFactory() {
		return downloadTaskFactory;
	}

	public UploadTaskFactory getUploadTaskFactory() {
		return uploadTaskFactory;
	}

	public RemoveTaskFactory getRemoveTaskFactory() {
		return removeTaskFactory;
	}

	public ListAllTaskFactory getListAllTaskFactory() {
		return listAllTaskFactory;
	}
	
}
