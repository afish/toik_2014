package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;
import pl.agh.iet.i.toik.cloudsync.logic.CloudType;

/**
 * Service using for obtaining cloud service providers.
 */
@Service
public class CloudServiceImpl implements CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudServiceImpl.class);

    private List<CloudInformation> cloudsList;

    public CloudServiceImpl(){
        cloudsList = new ArrayList<>();
    }

    @Override
    public CloudInformation getCloudByType(CloudType cloudType){
        for (CloudInformation ci : getAllClouds()){
            if (ci.getCloudType().equals(cloudType)){
                return ci;
            }
        }
        return null;
    }

    @Override
    public CloudInformation getCloudById(String cloudId){
        for (CloudInformation ci : getAllClouds()){
            if (ci.getId().equals(cloudId)){
                return ci;
            }
        }
        return null;
    }

    @Override
    public List<CloudInformation> getAllClouds() {
        logger.info("Getting all clouds");

        return cloudsList;
    }

	@Override
	public void registerCloud(Cloud cloud) {
        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        logger.info("Registering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        if(cloudsList.contains(cloud.getCloudInformation())){
            throw new IllegalArgumentException("Cloud with the same name id already exists. Cloud id: " + cloud.getCloudInformation().getId());
        }
        cloudsList.add(cloud.getCloudInformation());
	}

	@Override
	public void deregisterCloud(Cloud cloud) {
        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        logger.info("Deregistering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        cloudsList.remove(cloud.getCloudInformation());
	}
}
