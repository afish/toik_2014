package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

public class OpenLoginWindowEvent {

	private Account account;
	private FilesTabSheetView currentFilesTabSheetView;
	public OpenLoginWindowEvent(Account account,
			FilesTabSheetView currentFilesTabSheetView) {
		super();
		this.account = account;
		this.currentFilesTabSheetView = currentFilesTabSheetView;
	}
	public Account getAccount() {
		return account;
	}
	public FilesTabSheetView getCurrentFilesTabSheetView() {
		return currentFilesTabSheetView;
	}
	
	
	
	
}
