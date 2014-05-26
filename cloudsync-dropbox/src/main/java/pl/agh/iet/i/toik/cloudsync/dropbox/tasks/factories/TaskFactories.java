package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;


public class TaskFactories {

	private DownloadTaskFactory downloadTaskFactory;

	private UploadTaskFactory uploadTaskFactory;

	private RemoveTaskFactory removeTaskFactory;
	
	private ListAllTaskFactory listAllTaskFactory;
	

	public TaskFactories(DownloadTaskFactory downloadTaskFactory, UploadTaskFactory uploadTaskFactory, RemoveTaskFactory removeTaskFactory, ListAllTaskFactory listAllTaskFactory) {
		this.downloadTaskFactory = downloadTaskFactory;
		this.uploadTaskFactory = uploadTaskFactory;
		this.removeTaskFactory = removeTaskFactory;
		this.listAllTaskFactory = listAllTaskFactory;
	}

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
