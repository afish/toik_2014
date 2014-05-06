package pl.agh.iet.i.toik.cloudsync.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SomeService {

    private static Logger logger = LoggerFactory.getLogger(SomeService.class);

    public SomeService(){
        logger.info("Creating the service");
    }

    public void someMethod(){
        logger.info("Calling the method from the service");
    }

}
