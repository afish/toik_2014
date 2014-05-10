package pl.agh.iet.i.toik.cloudsync.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service using for obtaining cloud service providers.
 */
@Service
public class CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudService.class);

	public void getAllClouds() {
		logger.info("Getting all clouds");
		// TODO(afish): Implement this.
	}

	/** Adds the given cloud to this repository. */
	public void registerCloud(Cloud cloud) {
		logger.info("Registering cloud: " + cloud.getId() + ", name: " + cloud.getName());
		// TODO(afish): Implement this.
	}

	/** Removes the given cloud to this repository. */
	public void deregisterCloud(Cloud cloud) {
		logger.info("Deregistering cloud: " + cloud.getId() + ", name: " + cloud.getName());
		// TODO(afish): Implement this.
	}
}
