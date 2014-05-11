package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service using for obtaining cloud service providers.
 */
@Service
public class CloudServiceImpl implements CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudServiceImpl.class);

    @Autowired
    private PersistenceService persistenceService;
    private List<String> cloudsList;

    public CloudServiceImpl(){
        cloudsList = persistenceService.get("logic", "clouds_list");
        if(cloudsList == null){
            cloudsList = new ArrayList<>();
        }
    }

    @Override
    public List<CloudInformation> getAllClouds() {
        logger.info("Getting all clouds");

        List<CloudInformation> result = new ArrayList<>();
        for(String cloudName : cloudsList){
            result.add((CloudInformation)persistenceService.get("logic_cloud", cloudName));
        }

        return result;
    }

	@Override
	public void registerCloud(Cloud cloud) {
		logger.info("Registering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        String id = cloud.getCloudInformation().getId();
        if(cloudsList.contains(id)){
            throw new IllegalArgumentException("Cloud with the same name id already exists. Cloud id: " + id);
        }

        cloudsList.add(id);
        persistenceService.put("logic", "clouds_list", cloudsList);
        persistenceService.put("logic_cloud", cloud.getCloudInformation().getId(), cloud.getCloudInformation());
	}

	@Override
	public void deregisterCloud(Cloud cloud) {
		logger.info("Deregistering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        String id = cloud.getCloudInformation().getId();
        cloudsList.remove(id);
        persistenceService.put("logic", "clouds_list", cloudsList);
        persistenceService.put("logic_cloud", id, null);
	}
}
