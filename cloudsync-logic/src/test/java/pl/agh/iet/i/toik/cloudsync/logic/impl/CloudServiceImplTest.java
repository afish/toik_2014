package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class CloudServiceImplTest {

	private CloudServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new CloudServiceImpl();
		service.setPersistenceService(new FakePersistenceService());
	}

	@Test
	public void testGetAllClouds() {
		Cloud cloud = new Cloud() {

			@Override
			public CloudTask<CloudFile> upload(String sessionId,
					CloudFile directory, String fileName,
					InputStream fileInputStream, Long fileSize) {
				return null;
			}

			@Override
			public CloudTask<Boolean> remove(String sessionId, CloudFile file) {
				return null;
			}

			@Override
			public void logout(String sessionId) {
			}

			@Override
			public String login(Account account) {
				return null;
			}

			@Override
			public CloudTask<List<CloudFile>> listAllFiles(String sessionId,
					CloudFile directory) {
				return null;
			}

			@Override
			public CloudInformation getCloudInformation() {
				return new CloudInformation("cl_id", "Test cloud", this);
			}

			@Override
			public CloudTask<Boolean> download(String sessionId,
					CloudFile file, OutputStream outputStream) {
				return null;
			}
		};

		service.registerCloud(cloud);
		Assert.assertEquals(1, service.getAllClouds().size());
		service.deregisterCloud(cloud);
		Assert.assertEquals(0, service.getAllClouds().size());
	}
}
