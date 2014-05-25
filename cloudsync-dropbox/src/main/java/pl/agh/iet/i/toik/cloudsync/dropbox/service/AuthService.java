package pl.agh.iet.i.toik.cloudsync.dropbox.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class AuthService {

	private Map<String, DbxClient> sessionMap;

	public AuthService() {
		this.sessionMap = new HashMap<String, DbxClient>();
	}

	public String login(DropboxConfiguration dropboxConfiguration, Account account) {
		DbxRequestConfig dbxRequestConfig = dropboxConfiguration.getConfig();
		// TODO: token - get from account or where ?
		String token = null;
		try {
			String sessionId = account == null || token == null ? authenticate(dropboxConfiguration) : token;
			initializeSession(dbxRequestConfig, sessionId);
			return sessionId;
		} catch (DbxException e) {
			// TODO: logger
		} catch (IOException e) {
			// TODO: logger
		}
		return null;
	}

	public void logout(String sessionId) {
		sessionMap.remove(sessionId);
	}

	public DbxClient getDbxClient(String sessionId) {
		return sessionMap.get(sessionId);
	}

	//TODO: implement, verify
	private String authenticate(DropboxConfiguration dropboxConfiguration) throws DbxException, IOException {
		DbxWebAuthNoRedirect webAuth = dropboxConfiguration.getWebAuth();
		String authorizeUrl = webAuth.start();
		// TODO: get code
		String code = "";
		DbxAuthFinish authFinish = webAuth.finish(code);
		return authFinish.accessToken;
	}

	private void initializeSession(DbxRequestConfig dbxRequestConfig, String sessionId) {
		DbxClient dbxClient = new DbxClient(dbxRequestConfig, sessionId);
		sessionMap.put(sessionId, dbxClient);
	}

}
