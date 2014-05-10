package pl.agh.iet.i.toik.cloudsync.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Simple key-value persistent storage.
 */
@Service
public class PersistenceService {
	private static Logger logger = LoggerFactory.getLogger(PersistenceService.class);

	public <T> void put(String id, T obj) {
		logger.info("Putting key: " + id + ", value:" + obj);
		// TODO(kubicz): Implement this.
	}

	public <T> T get(String id) {
		logger.info("Getting key: " + id);
		return null;
		// TODO(kubicz): Implement this.
	}

	public void remove(String id) {
		logger.info("Removing key: " + id);
		// TODO(kubicz): Implement this.
	}
}
