package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class ListAllTaskParams {

	private CloudFile directory;

	public ListAllTaskParams(CloudFile directory) {
		if(directory != null)
            this.directory = directory;
        else
            this.directory = new CloudFile("/", null, true, "/", "", -1L);
	}

	public CloudFile getDirectory() {
		return directory;
	}	

}
