package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;

public class MoveFileEvent {


	private FilesTabView sourceTab;
	private FilesTabView destinationTab;
	public MoveFileEvent(FilesTabView sourceTab, FilesTabView destinationTab) {
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
