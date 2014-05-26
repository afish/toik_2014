package pl.agh.iet.i.toik.cloudsync.dropbox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

@Import(DropboxConfiguration.class)
public class DropboxCloudMain implements CommandLineRunner {

	@Autowired
	private DropboxCloud dropboxCloud;

	public static void main(String... args) {
		SpringApplication.run(DropboxCloudMain.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		String id = "testuser@gmail.com";
		String name = "dropbox-test";

		Map<String, Object> properties = new HashMap<String, Object>();
		Account account = new Account(id, name, properties);
		String authorizeUrl = dropboxCloud.getDropboxService().getDropboxConfiguration().getWebAuth().start();
		System.out.println("Go to: " + authorizeUrl);
		System.out.println("Paste the authorization code:");
		String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		properties.put("code", code);

		String sessionId = dropboxCloud.login(account);
		CloudFile directory = new CloudFile("/", new Date(), true, "/", "", 0L);
		CloudTask<List<CloudFile>> listAllTask = dropboxCloud.listAllFiles(sessionId, directory);
		List<CloudFile> files = listAllTask.get();
		for (CloudFile file : files) {
			System.out.println(file.getFullPath());
		}
	}

}
