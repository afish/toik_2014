package pl.agh.iet.i.toik.cloudsync.googledrive.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Value;
import pl.agh.iet.i.toik.cloudsync.googledrive.GoogleDriveCloud;
import pl.agh.iet.i.toik.cloudsync.logic.Cloud;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "pl.agh.iet.i.toik.cloudsync.googledrive.*")
@PropertySource("classpath:googleDrive.properties")
public class GoogleDriveConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public HttpTransport httpTransport() {
		return new NetHttpTransport();
	}

	@Bean
	public JsonFactory jsonFactory() {
		return new JacksonFactory();
	}

	@Bean
	public Cloud googleDriveCloud() {
		return new GoogleDriveCloud();
	}

	@Value("${clientId}")
	private String CLIENT_ID;

	@Value("${clientSecret}")
	private String CLIENT_SECRET;

	@Bean
	public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() {
		return new GoogleAuthorizationCodeFlow.Builder(
				httpTransport(), jsonFactory(), CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
				.setAccessType("online")
				.setApprovalPrompt("auto").build();
	}

}
