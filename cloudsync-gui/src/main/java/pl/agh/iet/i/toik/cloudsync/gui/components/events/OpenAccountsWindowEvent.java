package pl.agh.iet.i.toik.cloudsync.gui.components.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;

public class OpenAccountsWindowEvent {

	private FilesTabSheetView caller;

	public OpenAccountsWindowEvent(FilesTabSheetView caller) {
		super();
		this.caller = caller;
	}

	public FilesTabSheetView getCaller() {
		return caller;
	}
	
	
}
