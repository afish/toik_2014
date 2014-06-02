package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class RemoveTask extends CloudTask<Boolean> {

	@Autowired
	private DropboxCallable<Boolean> callable;

	public RemoveTask(DropboxCallable<Boolean> callable) {
		super(callable);
		this.callable = callable;
	}

	@Override
	public float getProgress() {
		return callable.getProgress();
	}

}
