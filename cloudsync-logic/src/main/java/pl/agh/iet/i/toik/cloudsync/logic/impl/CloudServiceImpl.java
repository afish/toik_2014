package pl.agh.iet.i.toik.cloudsync.logic.impl;

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
	public void registerCloud(Cloud cloud) {
		logger.info("Registering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());
		// TODO(afish): Implement this.
	}

	@Override
	public void deregisterCloud(Cloud cloud) {
		logger.info("Deregistering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());
		// TODO(afish): Implement this.
	}
}
