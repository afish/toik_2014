package pl.agh.iet.i.toik.cloudsync.dropbox.service;

import java.io.IOException;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class AuthService {
	
	private DbxClient dbxClient;
	private String currentPath;
	private String accessToken;

	public String login(DropboxConfiguration configuration, Account account) {
		DbxRequestConfig dbxRequestConfig = configuration.getConfig();
		// TODO: token - get from account or where ?
		String token = null;
		try {
			if (account == null || token == null) {
				accessToken = authenticate(configuration);
			} else {
				accessToken = token;
			}
			initializeDbxClient(dbxRequestConfig, accessToken);
			return accessToken;
		} catch (DbxException e) {
			//TODO: logger
		} catch (IOException e) {
			// TODO: logger
		}
		return null;
	}

	public void logout(String sessionId) {
		dbxClient = null;
		currentPath = null;
		accessToken = null;
	}
	
	public DbxClient getDbxClient() {
		return dbxClient;
	}

	private String authenticate(DropboxConfiguration configuration) throws DbxException, IOException {
		DbxWebAuthNoRedirect webAuth = configuration.getWebAuth();
		String authorizeUrl = webAuth.start();
		//TODO: get code
		String code = "";
		DbxAuthFinish authFinish = webAuth.finish(code);
		return authFinish.accessToken;
	}

	private void initializeDbxClient(DbxRequestConfig dbxRequestConfig, String accessToken) {
		DbxClient dbxClient = new DbxClient(dbxRequestConfig, accessToken);
		this.dbxClient = dbxClient;
	}

}
