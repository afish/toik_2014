package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class RemoveTaskParams {

	private CloudFile file;

	public RemoveTaskParams(CloudFile file) {
		this.file = file;
	}

	public CloudFile getFile() {
		return file;
	}

}
