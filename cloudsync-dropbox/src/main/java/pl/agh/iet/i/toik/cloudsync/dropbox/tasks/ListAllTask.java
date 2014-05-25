package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class ListAllTask extends CloudTask<List<CloudFile>> {

	@Autowired
	private Callable<List<CloudFile>> callable;

	public ListAllTask(Callable<List<CloudFile>> callable) {
		super(callable);
	}

	@Override
	public float getProgress() {
		// dropbox api does not provide such feature
		return 0;
	}

}
