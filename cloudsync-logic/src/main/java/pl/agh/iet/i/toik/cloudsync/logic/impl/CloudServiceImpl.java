package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service using for obtaining cloud service providers.
 */
@Service
public class CloudServiceImpl implements CloudService {
	private static Logger logger = LoggerFactory.getLogger(CloudServiceImpl.class);

    @Autowired
    private PersistenceService persistenceService;

    private List<CloudInformation> loadCloudsList(){
        if(persistenceService.has("logic", "clouds_list")){
            return (List) persistenceService.get("logic", "clouds_list");
        }else{
            return new ArrayList<>();
        }
    }

    private void saveCloudsList(List<CloudInformation> cloudsList){
        ArrayList<CloudInformation> cloudsArrayList = new ArrayList<>(cloudsList);
        persistenceService.put("logic", "clouds_list", cloudsArrayList);
    }

    @Override
    public List<CloudInformation> getAllClouds() {
        logger.info("Getting all clouds");

        return loadCloudsList();
    }

	@Override
	public void registerCloud(Cloud cloud) {
        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        logger.info("Registering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        List<CloudInformation> cloudsList = loadCloudsList();
        if(cloudsList.contains(cloud.getCloudInformation())){
            throw new IllegalArgumentException("Cloud with the same name id already exists. Cloud id: " + cloud.getCloudInformation().getId());
        }
        cloudsList.add(cloud.getCloudInformation());
        saveCloudsList(cloudsList);
	}

	@Override
	public void deregisterCloud(Cloud cloud) {
        if(cloud == null){
            throw new IllegalArgumentException("Cloud cannot be null");
        }

        logger.info("Deregistering cloud: " + cloud.getCloudInformation().getId() + ", name: "
				+ cloud.getCloudInformation().getHumanReadableName());

        List<CloudInformation> cloudsList = loadCloudsList();
        cloudsList.remove(cloud.getCloudInformation());
        saveCloudsList(cloudsList);
	}
}
