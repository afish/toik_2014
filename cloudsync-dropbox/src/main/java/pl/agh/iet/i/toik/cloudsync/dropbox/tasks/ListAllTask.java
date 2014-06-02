package pl.agh.iet.i.toik.cloudsync.dropbox.tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class ListAllTask extends CloudTask<List<CloudFile>> {

	@Autowired
	private DropboxCallable<List<CloudFile>> callable;

	public ListAllTask(DropboxCallable<List<CloudFile>> callable) {
		super(callable);
		this.callable = callable;
	}

	@Override
	public float getProgress() {
		return callable.getProgress();
	}

}
