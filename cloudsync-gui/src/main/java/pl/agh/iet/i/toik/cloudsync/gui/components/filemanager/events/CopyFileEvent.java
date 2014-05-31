package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;

public class CopyFileEvent {

	private FilesTabView sourceTab;
	private FilesTabView destinationTab;
	
	public CopyFileEvent(FilesTabView sourceTab, FilesTabView destinationTab) {
		this.sourceTab = sourceTab;
		this.destinationTab = destinationTab;
	}

	public FilesTabView getSourceTab() {
		return sourceTab;
	}

	public FilesTabView getDestinationTab() {
		return destinationTab;
	}
	
	
	
	
}
