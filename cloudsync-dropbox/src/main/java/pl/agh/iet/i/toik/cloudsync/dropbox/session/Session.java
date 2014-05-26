package pl.agh.iet.i.toik.cloudsync.dropbox.session;

import pl.agh.iet.i.toik.cloudsync.logic.Account;

import com.dropbox.core.DbxClient;

public class Session {

	private String sessionId;
	private Account account;
	private DbxClient dbxClient;
	
	public Session(String sessionId, Account account, DbxClient dbxClient) {
		this.sessionId = sessionId;
		this.account = account;
		this.dbxClient = dbxClient;
	}

	public DbxClient getDbxClient() {
		return dbxClient;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Account getAccount() {
		return account;
	}
	
}
