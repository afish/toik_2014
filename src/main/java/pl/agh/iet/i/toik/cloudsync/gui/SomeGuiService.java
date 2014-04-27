package pl.agh.iet.i.toik.cloudsync.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.iet.i.toik.cloudsync.logic.SomeService;

@Service
public class SomeGuiService {

    private static Logger logger = LoggerFactory.getLogger(SomeGuiService.class);

    @Autowired
    private SomeService someService;

    public SomeGuiService(){
        logger.info("Constructing gui service");
    }

    public void start(){
        logger.info("Starting GUI");
        someService.someMethod();
    }
}
