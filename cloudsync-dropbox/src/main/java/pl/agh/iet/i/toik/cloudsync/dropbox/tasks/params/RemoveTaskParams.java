package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import java.util.Date;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class RemoveTaskParams {

	private CloudFile file;

	public RemoveTaskParams(CloudFile file) {
		this.file = file != null ? file : new CloudFile("/", new Date(), true, "/", "", -1L);
	}

	public CloudFile getFile() {
		return file;
	}

}
