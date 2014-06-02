package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DropboxCallable;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.RemoveTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.RemoveTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

public class RemoveTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(RemoveTaskFactory.class);
	
	public RemoveTask create(DbxClient dbxClient, CloudFile file) {
		RemoveTaskParams removeTaskParams = new RemoveTaskParams(file);
		DropboxCallable<Boolean> callable = this.getCallable(dbxClient, removeTaskParams);
		RemoveTask removeTask = new RemoveTask(callable);
		return removeTask;
	}

	private DropboxCallable<Boolean> getCallable(final DbxClient client, final RemoveTaskParams removeTaskParams) {
		final String file = removeTaskParams.getFile().getFullPath();

		return new DropboxCallable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				try {
					this.setProgress(0.1f);
					client.delete(file);
					this.setProgress(1.0f);
					return true;
				} catch (DbxException e) {
					 logger.error("Problem with removing", e.getMessage());
				}
				return false;
			}
		};
	}

}
