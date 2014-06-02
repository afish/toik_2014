package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import java.util.concurrent.Callable;

public abstract class DropboxCallable<T> implements Callable<T> {

	private float progress = 0.0f;
	
	public float getProgress() {
		return progress;
	}
	
	public void setProgress(float progress) {
		this.progress = progress;
	}
}
