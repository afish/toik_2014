package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.logic.Account;

public class AddAccountEvent {

	private Account account;
	public AddAccountEvent(Account account) {
		this.account = account;
	}
	public Account getAccount() {
		return account;
	}
	
}
