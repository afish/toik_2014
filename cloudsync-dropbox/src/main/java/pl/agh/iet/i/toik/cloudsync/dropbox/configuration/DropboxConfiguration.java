package pl.agh.iet.i.toik.cloudsync.dropbox.configuration;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.DownloadTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.ListAllTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.RemoveTaskFactory;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.TaskFactories;
import pl.agh.iet.i.toik.cloudsync.dropbox.tasks.factories.UploadTaskFactory;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

@ComponentScan
@Configuration
@SuppressWarnings("serial")
public class DropboxConfiguration implements Serializable {

	protected static final String APP_KEY = "hn2bx52zyvavui7";
	protected static final String APP_SECRET = "coqhmdex9jgg5bs";
	protected static final String APP_NAME = "TOiK-DbxCloud";

	/**
	 * Fetches Dropbox WebAuth object.
	 * 
	 * @return Dropbox authorization object
	 */
	@Bean
	public DbxWebAuthNoRedirect getWebAuth() {
		return new DbxWebAuthNoRedirect(this.getConfig(), this.getAppInfo());
	}

	/**
	 * Fetches Dropbox config.
	 * 
	 * @return Dropbox configuration object
	 */
	@Bean
	public DbxRequestConfig getConfig() {
		return new DbxRequestConfig(APP_NAME, Locale.getDefault().toString());
	}

	/**
	 * Fetches Dropbox application info.
	 * 
	 * @return Dropbox application info
	 */
	@Bean
	public DbxAppInfo getAppInfo() {
		return new DbxAppInfo(APP_KEY, APP_SECRET);
	}
	
	@Bean
	public TaskFactories getTaskFactories() {
		DownloadTaskFactory downloadTaskFactory = new DownloadTaskFactory();
		UploadTaskFactory uploadTaskFactory = new UploadTaskFactory();
		RemoveTaskFactory removeTaskFactory = new RemoveTaskFactory();
		ListAllTaskFactory listAllTaskFactory = new ListAllTaskFactory();
		TaskFactories taskFactories = new TaskFactories(downloadTaskFactory, uploadTaskFactory, removeTaskFactory, listAllTaskFactory);
		return taskFactories;
	}

}
