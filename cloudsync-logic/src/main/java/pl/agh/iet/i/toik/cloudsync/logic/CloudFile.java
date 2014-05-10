package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.Date;

/**
 * A file living in the cloud. Feel free to add new fields if needed.
 */
public class CloudFile {
	private final String name;
	private final Date creationDate;
	private final boolean isDirectory;

	public CloudFile(String name, Date creationDate, boolean isDirectory) {
		this.name = name;
		this.creationDate = creationDate;
		this.isDirectory = isDirectory;
	}

	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public boolean isDirectory() {
		return this.isDirectory;
	}
}
