package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

@Service
public class PersistenceServiceImpl implements PersistenceService {
	private static Logger logger = LoggerFactory.getLogger(PersistenceServiceImpl.class);

	@Override
	public <T> void put(String prefix, String id, T obj) {
		logger.info("Putting key: " + id + ", value:" + obj + ", prefix:" + prefix);
		// TODO(kubicz): Implement this.
	}

	@Override
	public <T> T get(String prefix, String id) {
		logger.info("Getting key: " + id + ", prefix:" + prefix);
		return null;
		// TODO(kubicz): Implement this.
	}

	@Override
	public void remove(String prefix, String id) {
		logger.info("Removing key: " + id + ", prefix:" + prefix);
		// TODO(kubicz): Implement this.
	}

    @Override
    public boolean has(String prefix, String id) {
        logger.info("Checking existence of key: " + id + ", prefix:" + prefix);
        return false;
        // TODO(kubicz): Implement this.
    }

    @Override
    public int getNextFromSequence(String prefix, String sequenceName) {
        logger.info("Getting sequence: " + prefix + "," + sequenceName);
        return -1;
        // TODO(kubicz): Implement this.
    }
}
