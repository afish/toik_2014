package pl.agh.iet.i.toik.cloudsync.logic;

/**
 * Identifies session within a cloud.
 */
public class CloudSession {
	private final CloudInformation cloudInformation;
	private final String sessionId;

	public CloudInformation getCloudInformation() {
		return cloudInformation;
	}

	public String getSessionId() {
		return sessionId;
	}

	public CloudSession(CloudInformation cloudInformation, String sessionId) {
		this.cloudInformation = cloudInformation;
		this.sessionId = sessionId;
	}
}
