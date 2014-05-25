package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.io.OutputStream;
import java.util.concurrent.Callable;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DownloadTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.DownloadTaskParams;

import com.dropbox.core.DbxClient;

public class DownloadTaskFactory {

	public DownloadTask create(DbxClient client, OutputStream outputStream, String file) {
		DownloadTaskParams downloadTaskParams = new DownloadTaskParams(outputStream, file);
		Callable<Boolean> callable = this.getCallable(client, downloadTaskParams);
		DownloadTask downloadTask = new DownloadTask(callable);
		return downloadTask;
	}
	
	private Callable<Boolean> getCallable(final DbxClient client, final DownloadTaskParams downloadTaskParams) {
		final String file = downloadTaskParams.getFile();
		final OutputStream outputStream = downloadTaskParams.getOutputStream();
		return new Callable<Boolean>() {			
			@Override
			public Boolean call() throws Exception {
				try {
					client.getFile(file, null, outputStream);
					return true;
				} catch (Exception e) {
					// TODO: logger
					return false;
				}
			}
		};
	}
	
}
