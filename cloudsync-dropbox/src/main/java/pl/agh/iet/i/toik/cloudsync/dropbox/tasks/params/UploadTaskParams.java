package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import java.io.InputStream;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class UploadTaskParams {

	private CloudFile directory;
	private String fileName;
	private InputStream fileInputStream;
	private Long fileSize;

	public UploadTaskParams(CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize) {
		this.directory = directory;
		this.fileName = fileName;
		this.fileInputStream = fileInputStream;
		this.fileSize = fileSize;
	}

	public CloudFile getDirectory() {
		return directory;
	}

	public String getFileName() {
		return fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public Long getFileSize() {
		return fileSize;
	}

}
