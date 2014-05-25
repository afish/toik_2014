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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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

	}
}
