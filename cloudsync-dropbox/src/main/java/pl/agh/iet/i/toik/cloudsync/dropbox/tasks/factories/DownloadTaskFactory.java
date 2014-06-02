package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DownloadTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DropboxCallable;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.DownloadTaskParams;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;

public class DownloadTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(DownloadTaskFactory.class);

	public DownloadTask create(DbxClient dbxClient, OutputStream outputStream, String file) {
		DownloadTaskParams downloadTaskParams = new DownloadTaskParams(outputStream, file);
		DropboxCallable<Boolean> callable = this.getCallable(dbxClient, downloadTaskParams);
		DownloadTask downloadTask = new DownloadTask(callable);
		return downloadTask;
	}

	private DropboxCallable<Boolean> getCallable(final DbxClient client, final DownloadTaskParams downloadTaskParams) {
		final String file = downloadTaskParams.getFile();
		final OutputStream outputStream = downloadTaskParams.getOutputStream();

		return new DropboxCallable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				try {
					logger.info("Dropbox - downloading started");
					this.setProgress(0.1f);
					client.getFile(file, null, outputStream);
					logger.info("Dropbox - downloading finished");
					this.setProgress(1.0f);
					return true;
				} catch (DbxException e) {
					logger.error("Problem with downloading", e.getMessage());
				} catch (IOException e) {
					logger.error("Problem with downloading", e.getMessage());
				}
				return false;
			}
		};
	}

}
