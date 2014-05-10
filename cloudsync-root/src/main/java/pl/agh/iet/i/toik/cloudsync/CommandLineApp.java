package pl.agh.iet.i.toik.cloudsync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

@Import(DefaultConfiguration.class)
public class CommandLineApp extends SpringBootServletInitializer implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(CommandLineApp.class);

	@Autowired
	private LogicService someService;

	public static void main(String... args) {
		logger.info("Hello: Application starting.");
		SpringApplication.run(CommandLineApp.class, args);
		logger.info("Hello: Application exit.");
	}

	@Override
	public void run(String... strings) throws Exception {
		someService.getAllClouds();
		System.out.println("Hello: Started executing code");
	}

}
