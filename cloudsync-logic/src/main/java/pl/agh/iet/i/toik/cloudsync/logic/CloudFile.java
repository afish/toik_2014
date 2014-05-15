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

	public CloudFile(String name, Date creationDate, boolean isDirectory, String fullPath, String id) {
		this.name = name;
		this.creationDate = creationDate;
		this.isDirectory = isDirectory;
        this.fullPath = fullPath;
        this.id = id;
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

    public String toString(){
        return "[File: " + fullPath + " with id " + id + "]";
    }
}
