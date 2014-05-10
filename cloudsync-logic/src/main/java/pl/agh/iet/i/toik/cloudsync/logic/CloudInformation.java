package pl.agh.iet.i.toik.cloudsync.logic;

/**
 * Basic cloud information set.
 */
public class CloudInformation {
	private final String id;
	private final String humanReadableName;

	public CloudInformation(String id, String humanReadableName) {
		this.id = id;
		this.humanReadableName = humanReadableName;
	}

	public String getId() {
		return id;
	}

	public String getHumanReadableName() {
		return humanReadableName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CloudInformation other = (CloudInformation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
