package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.RemoveTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.RemoveTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

public class RemoveTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(RemoveTaskFactory.class);
	
	public RemoveTask create(DbxClient dbxClient, CloudFile file) {
		RemoveTaskParams removeTaskParams = new RemoveTaskParams(file);
		Callable<Boolean> callable = this.getCallable(dbxClient, removeTaskParams);
		RemoveTask removeTask = new RemoveTask(callable);
		return removeTask;
	}

	private Callable<Boolean> getCallable(final DbxClient client, final RemoveTaskParams removeTaskParams) {
		final String file = removeTaskParams.getFile().getFullPath();

		return new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				try {
					client.delete(file);
					return true;
				} catch (DbxException e) {
					 logger.error("Problem with removing", e.getMessage());
				}
				return false;
			}
		};
	}

}
