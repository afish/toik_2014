package pl.agh.iet.i.toik.cloudsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;
import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;
import pl.agh.iet.i.toik.cloudsync.logic.impl.CloudServiceImpl;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DefaultConfiguration {


	@Autowired
	@Qualifier("googleDriveCloud")
	private Cloud googleDriveCloud;
	
	@Autowired
	@Qualifier("dropboxCloud")
	private Cloud dropboxCloud;

    @Autowired
    @Qualifier("onedriveCloud")
    private Cloud onedriveCloud;
    
    @Autowired
    private PersistenceService persistenceService;
    
    @Bean
    public CloudService cloudService() {
    	
    	 CloudServiceImpl cloudService = new CloudServiceImpl();
    	 cloudService.setPersistenceService(persistenceService);
    	 cloudService.registerCloud(googleDriveCloud);
    	 cloudService.registerCloud(dropboxCloud);
    	 cloudService.registerCloud(onedriveCloud);
    	 return   cloudService;
    }
}