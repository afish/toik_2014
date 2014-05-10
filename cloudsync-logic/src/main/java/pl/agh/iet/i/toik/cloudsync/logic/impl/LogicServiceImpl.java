package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

/**
 * Logic service implementation.
 */
@Service
public class LogicServiceImpl implements LogicService {
	private static Logger logger = LoggerFactory.getLogger(LogicServiceImpl.class);

	@Override
	public List<CloudInformation> getAllClouds() {
		logger.info("Getting all clouds");
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudSession login(CloudInformation cloudInformation, Account account) {
		logger.info("Logging in: " + cloudInformation + ", " + account);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public void logout(CloudSession session) {
		logger.info("Logging out: " + session);
		// TODO(afish): Implement this.
	}

	@Override
	public CloudTask<Boolean> move(CloudSession sessionFrom, String fromFileName,
			CloudSession sessionTo, String toFileName, Callable<Boolean> callback) {
		logger.info("Moving: " + fromFileName + " -> " + toFileName);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudTask<Boolean> copy(CloudSession session, String srcFileName, String destFileName,
			Callable<Boolean> callback) {
		logger.info("Copying: " + srcFileName + " -> " + destFileName);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudTask<List<CloudFile>> listFiles(CloudSession sessionFrom, String directory,
			Callable<List<CloudFile>> callback) {
		logger.info("Listing all files: " + directory);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudTask<Boolean> delete(CloudSession session, String fileName,
			Callable<Boolean> callback) {
		logger.info("Deleting: " + fileName);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudTask<Boolean> download(CloudSession sessionId, String absoluteFileName,
			OutputStream outputStream, Callable<Boolean> callback) {
		logger.info("Downloading: " + absoluteFileName);
		// TODO(afish): Implement this.
		return null;
	}

	@Override
	public CloudTask<Boolean> upload(CloudSession sessionId, String absoluteFileName,
			InputStream fileInputStream, Callable<Boolean> callback) {
		logger.info("Uploading: " + absoluteFileName);
		// TODO(afish): Implement this.
		return null;
	}
}
