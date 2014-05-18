package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.Date;

/**
 * A file living in the cloud. Feel free to add new fields if needed.
 */
public class CloudFile {
	private final String name;
	private final Date creationDate;
	private final boolean isDirectory;
	private final String fullPath;
	private final String id;
	/** Size in bytes. -1 means unknown size. */
	private final Long size;

	public CloudFile(String name, Date creationDate, boolean isDirectory, String fullPath,
			String id, Long size) {
		this.name = name;
		this.creationDate = creationDate;
		this.isDirectory = isDirectory;
		this.fullPath = fullPath;
		this.id = id;
		this.size = size;
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

	public String getFullPath() {
		return fullPath;
	}

	public String getId() {
		return id;
	}

	public Long getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "CloudFile [name=" + name + ", creationDate=" + creationDate + ", isDirectory="
				+ isDirectory + ", fullPath=" + fullPath + ", id=" + id + "]";
	}
}
