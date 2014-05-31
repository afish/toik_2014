package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;

public class AddAccountEvent {

	private Account account;
	private CloudInformation cloudInformation;
	public AddAccountEvent(Account account, CloudInformation cloudInformation) {
		this.account = account;
		this.cloudInformation = cloudInformation;
	}
	public Account getAccount() {
		return account;
	}
	public CloudInformation getCloudInformation() {
		return cloudInformation;
	}
	
	
	
}
