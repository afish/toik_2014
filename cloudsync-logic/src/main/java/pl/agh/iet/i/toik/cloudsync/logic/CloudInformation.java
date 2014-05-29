package pl.agh.iet.i.toik.cloudsync.logic;

import java.io.Serializable;

/**
 * Basic cloud information set.
 */
public class CloudInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String humanReadableName;
    private final Cloud cloud;
    private final CloudType cloudType;

	public CloudInformation(String id, String humanReadableName, Cloud cloud, CloudType cloudType) {
		this.id = id;
		this.humanReadableName = humanReadableName;
        this.cloud = cloud;
        this.cloudType = cloudType;
	}

	public String getId() {
		return id;
	}

	public String getHumanReadableName() {
		return humanReadableName;
	}

    public CloudType getCloudType() {
		return cloudType;
	}

	public Cloud getCloud(){ return cloud; }

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
