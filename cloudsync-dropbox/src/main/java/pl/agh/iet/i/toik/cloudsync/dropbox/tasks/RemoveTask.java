package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class RemoveTask extends CloudTask<Boolean> {

	@Autowired
	private Callable<Boolean> callable;

	public RemoveTask(Callable<Boolean> callable) {
		super(callable);
	}

	@Override
	public float getProgress() {
		// dropbox api does not provide such feature
		return 0;
	}

}