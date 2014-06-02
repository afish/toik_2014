package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class UploadTask extends CloudTask<CloudFile> {

	@Autowired
	private DropboxCallable<CloudFile> callable;

	public UploadTask(DropboxCallable<CloudFile> callable) {
		super(callable);
		this.callable = callable;
	}

	@Override
	public float getProgress() {
		return callable.getProgress();
	}

}
