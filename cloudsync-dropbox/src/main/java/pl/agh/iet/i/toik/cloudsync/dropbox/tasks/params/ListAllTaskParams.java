package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class ListAllTaskParams {

	private CloudFile directory;

	public ListAllTaskParams(CloudFile directory) {
		this.directory = directory;
	}

	public CloudFile getDirectory() {
		return directory;
	}	

}
