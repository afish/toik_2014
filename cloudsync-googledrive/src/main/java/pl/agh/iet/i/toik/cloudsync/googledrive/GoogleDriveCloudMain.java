package pl.agh.iet.i.toik.cloudsync.googledrive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import pl.agh.iet.i.toik.cloudsync.googledrive.config.GoogleDriveConfiguration;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;

@Import(GoogleDriveConfiguration.class)
public class GoogleDriveCloudMain implements CommandLineRunner {

	@Autowired
	private Cloud googleDriveCloud;

	public static void main(String... args) {
		SpringApplication.run(GoogleDriveCloudMain.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Name "+googleDriveCloud.getCloudInformation().getHumanReadableName());
	}
}
