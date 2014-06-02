package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import java.util.Date;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class ListAllTaskParams {

	private CloudFile directory;

	public ListAllTaskParams(CloudFile directory) {
		this.directory = directory != null ? directory : new CloudFile("/", new Date(), true, "/", "", -1L);
	}

	public CloudFile getDirectory() {
		return directory;
	}	

}
