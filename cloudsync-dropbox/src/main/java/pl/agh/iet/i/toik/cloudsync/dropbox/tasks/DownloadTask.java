package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class DownloadTask extends CloudTask<Boolean> {
	
	@Autowired
	private DropboxCallable<Boolean> callable;

	public DownloadTask(DropboxCallable<Boolean> callable) {
		super(callable);
		this.callable = callable;
	}

	@Override
	public float getProgress() {
		return callable.getProgress();
	}

}
