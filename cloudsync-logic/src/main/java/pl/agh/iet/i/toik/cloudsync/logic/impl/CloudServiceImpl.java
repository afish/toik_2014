package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;

/**
 * Service using for obtaining cloud service providers.
 */
@Service
public class CloudServiceImpl implements CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudServiceImpl.class);

	@Override
	public List<Cloud> getAllClouds() {
		logger.info("Getting all clouds");
		return null;
		// TODO(afish): Implement this.
	}

	@Override
	public void registerCloud(Cloud cloud) {
		logger.info("Registering cloud: " + cloud.getId() + ", name: " + cloud.getName());
		// TODO(afish): Implement this.
	}

	@Override
	public void deregisterCloud(Cloud cloud) {
		logger.info("Deregistering cloud: " + cloud.getId() + ", name: " + cloud.getName());
		// TODO(afish): Implement this.
	}
}
