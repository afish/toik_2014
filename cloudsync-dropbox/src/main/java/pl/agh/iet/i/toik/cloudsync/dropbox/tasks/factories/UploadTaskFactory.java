package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.io.InputStream;
import java.util.concurrent.Callable;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.UploadTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.UploadTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxStreamWriter;
import com.dropbox.core.DbxWriteMode;

public class UploadTaskFactory {

	public UploadTask create(DbxClient client, CloudFile directory, String fileName, InputStream fileInputStream,
			Long fileSize) {
		UploadTaskParams uploadTaskParams = new UploadTaskParams(directory, fileName, fileInputStream, fileSize);
		Callable<CloudFile> callable = this.getCallable(client, uploadTaskParams);
		UploadTask uploadTask = new UploadTask(callable);
		return uploadTask;
	}

	private Callable<CloudFile> getCallable(final DbxClient client, final UploadTaskParams uploadTaskParams) {
		final CloudFile directory = uploadTaskParams.getDirectory();
		final String fileName = uploadTaskParams.getFileName();
		final InputStream fileInputStream = uploadTaskParams.getFileInputStream();
		final Integer fileSize = uploadTaskParams.getFileSize().intValue();
		return new Callable<CloudFile>() {
			
			@Override
			public CloudFile call() throws Exception {
				try {
					//TODO: implement
					String uploadingID = client.chunkedUploadFirst(fileSize, new DbxStreamWriter.InputStreamCopier(fileInputStream));
					String path = directory.getFullPath() + fileName;
					client.chunkedUploadFinish(path, DbxWriteMode.add(), uploadingID);
					
					// setprogress finished

					//TODO: return value
				} catch (DbxException e) {
					// TODO: logger
					return null;
				}
				return null;
			}
		};
	}

}
