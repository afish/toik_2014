package pl.agh.iet.i.toik.cloudsync.dropbox.tasks.params;

import java.io.OutputStream;

public class DownloadTaskParams {

	private OutputStream outputStream;
	private String file;

	public DownloadTaskParams(OutputStream outputStream, String file) {
		this.outputStream = outputStream;
		this.file = file;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public String getFile() {
		return file;
	}

}
