/**
 * 
 */
package pl.agh.iet.i.toik.cloudsync.gui.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;

/**
 * @author daniel
 *
 */

public class CloudServiceMockImpl implements CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudServiceMockImpl.class);
	
	List<CloudInformation> clouds;
	
	public CloudServiceMockImpl() {
		clouds = new ArrayList<CloudInformation>();
		
		Cloud dropbox = new CloudMock("d1", "Dropbox");
		Cloud oneDrive = new CloudMock("od1", "One Drive");
		Cloud googleDrive = new CloudMock("gd1", "Google Drive");
		logger.info("Adding clouds");
		clouds.add(dropbox.getCloudInformation());
		clouds.add(oneDrive.getCloudInformation());
		clouds.add(googleDrive.getCloudInformation());
	}
	
	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.CloudService#getAllClouds()
	 */
	@Override
	public List<CloudInformation> getAllClouds() {
		logger.info("Getting all clouds");
		return clouds;
	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.CloudService#registerCloud(pl.agh.iet.i.toik.cloudsync.logic.Cloud)
	 */
	@Override
	public void registerCloud(Cloud cloud) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see pl.agh.iet.i.toik.cloudsync.logic.CloudService#deregisterCloud(pl.agh.iet.i.toik.cloudsync.logic.Cloud)
	 */
	@Override
	public void deregisterCloud(Cloud cloud) {
		// TODO Auto-generated method stub

	}

}
