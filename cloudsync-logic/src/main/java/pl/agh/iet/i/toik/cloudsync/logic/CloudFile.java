package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.Date;

/**
 * A file living in the cloud. Feel free to add new fields if needed.
 */
public class CloudFile {
	private final String name;
	private final Date creationDate;

	public CloudFile(String name, Date creationDate) {
		this.name = name;
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloudFile other = (CloudFile) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
