package pl.agh.iet.i.toik.cloudsync.dropbox.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.dropbox.configuration.DropboxConfiguration;
import pl.agh.iet.i.toik.cloudsync.dropbox.session.Session;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

@Service
public class AuthService {

	private static Logger logger = LoggerFactory.getLogger(AuthService.class);
	 
	private Map<String, Session> sessionMap;

	public AuthService() {
		this.sessionMap = new HashMap<String, Session>();
	}

	public String login(DropboxConfiguration dropboxConfiguration, Account account) {
		DbxRequestConfig dbxRequestConfig = dropboxConfiguration.getConfig();
		// TODO: token - get from account or where ?
		String token = null;
		try {
			String sessionId = account == null || token == null ? authenticate(dropboxConfiguration) : token;
			initializeSession(dbxRequestConfig, sessionId, account);
			return sessionId;
		} catch (DbxException e) {
			 logger.error("Problem with login", e.getMessage());
		} catch (IOException e) {
			 logger.error("Problem with login", e.getMessage());
		}
		return null;
	}

	public void logout(String sessionId) {
		sessionMap.remove(sessionId);
	}

	public Session getSession(String sessionId) {
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

	private void initializeSession(DbxRequestConfig dbxRequestConfig, String sessionId, Account account) {
		DbxClient dbxClient = new DbxClient(dbxRequestConfig, sessionId);
		Session session = new Session(sessionId, account, dbxClient);
		sessionMap.put(sessionId, session);
	}

}
