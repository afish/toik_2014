package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.DropboxCallable;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.UploadTask;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params.UploadTaskParams;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxWriteMode;

public class UploadTaskFactory {

	private static Logger logger = LoggerFactory.getLogger(UploadTaskFactory.class);

	public UploadTask create(DbxClient dbxClient, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
		UploadTaskParams uploadTaskParams = new UploadTaskParams(directory, fileName, fileInputStream, fileSize);
		UploadTask uploadTask = getUploadTask(dbxClient, uploadTaskParams);
		return uploadTask;
	}

	private UploadTask getUploadTask(final DbxClient client, final UploadTaskParams uploadTaskParams) {
		final CloudFile directory = uploadTaskParams.getDirectory();
		final String fileName = uploadTaskParams.getFileName();
		final InputStream fileInputStream = uploadTaskParams.getFileInputStream();
		final Integer fileSize = uploadTaskParams.getFileSize().intValue();
		final String targetPath = (directory.getFullPath() + "/" + fileName).replaceAll("//", "/");

		return new UploadTask(new DropboxCallable<CloudFile>() {
			@Override
			public CloudFile call() throws Exception {
				try {
					this.setProgress(0.1f);
					DbxEntry.File uploadedFile = null;
					logger.info("Dropbox - Uploading started for targetpath " + targetPath + ", filesize: " + fileSize);
					uploadedFile = client.uploadFile(targetPath, DbxWriteMode.add(), fileSize, fileInputStream);
					this.setProgress(1.0f);
					logger.info("Dropbox - upload finished");
					// TODO: return better values
					CloudFile returnValue = new CloudFile(uploadedFile.name, uploadedFile.lastModified, uploadedFile.isFolder(), targetPath, "2", uploadedFile.numBytes);
					return returnValue;
				} catch (Throwable e) {
					logger.error("Problem with uploading", e.getMessage());
				} finally {
					if (fileInputStream != null) {
						try {
							logger.info("Dropbox - closing upload stream");
							fileInputStream.close();
							logger.info("Dropbox - upload stream closed");
						} catch (IOException e) {
							logger.error("Problem with uploading: unable to close input stream", e.getMessage());
						}
					}
				}
				return new CloudFile("/", new Date(), true, "/", "", -1L);
			}
		});
	}
}
