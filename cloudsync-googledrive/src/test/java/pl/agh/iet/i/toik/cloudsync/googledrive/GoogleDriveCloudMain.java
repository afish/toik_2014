package pl.agh.iet.i.toik.cloudsync.googledrive;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import pl.agh.iet.i.toik.cloudsync.googledrive.config.GoogleDriveConfiguration;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Import(GoogleDriveConfiguration.class)
public class GoogleDriveCloudMain implements CommandLineRunner {

	@Autowired
	private Cloud googleDriveCloud;

	@Autowired
	private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

	private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	public static void main(String... args) {
		SpringApplication.run(GoogleDriveCloudMain.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Name "+googleDriveCloud.getCloudInformation().getHumanReadableName());

		String url = googleAuthorizationCodeFlow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
		System.out.println("Please open the following URL in your browser then type the authorization code:");
		System.out.println("  " + url);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		Map<String, Object> propList = new HashMap<>();
		propList.put("cloud.google.code", code);
		Account account = new Account("1", "test", propList);
		String id = googleDriveCloud.login(account);
		CloudTask<List<CloudFile>> task =  googleDriveCloud.listAllFiles(id, null);
		task.run();


		System.out.println("Please insert id of file");
		String fileID = br.readLine();
		OutputStream os = new FileOutputStream("TOIK.txt");
		CloudFile cloudFile = new CloudFile("TOIK.txt", new Date(), false, "test", fileID, 1L);
		CloudTask<Boolean> task1 = googleDriveCloud.download(id, cloudFile, os);
		task1.run();


		System.out.println("Please insert id of dir");
		String dirID = br.readLine();
		InputStream is = new FileInputStream("TOIK2.txt");
		CloudFile dir = new CloudFile("TOIK", new Date(), false, "test", dirID, 1L);
		CloudTask<CloudFile> task2 = googleDriveCloud.upload(id, dir, "TOIK2.txt", is, 1L);
		task2.run();
		is = new FileInputStream("TOIK2.txt");
		CloudTask<CloudFile> task3 = googleDriveCloud.upload(id, null, "TOIK2.txt", is, 1L);
		task3.run();

		System.out.println("Please insert id of file");
		String fileIDRemove = br.readLine();
		CloudFile cloudFile2 = new CloudFile("TOIK2.txt", new Date(), false, "test", fileIDRemove, 1L);
		CloudTask<Boolean> task4 = googleDriveCloud.remove(id, cloudFile2);
		task4.run();

		googleDriveCloud.logout(id);

	}
}
