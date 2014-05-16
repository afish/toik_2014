package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;

public class FileSelectedEvent {

	private FilesTabSheetView tabSheetView;

	public FileSelectedEvent(FilesTabSheetView tabSheetView) {
		super();
		this.tabSheetView = tabSheetView;
	}

	public FilesTabSheetView getTabSheetView() {
		return tabSheetView;
	}
	
	
}
