package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;

public class ChangePathEvent {

	private String currentPath;
	private FilesTabView sourceTab;
	public ChangePathEvent(String currentPath, FilesTabView sourceTab) {
		this.currentPath = currentPath;
		this.sourceTab = sourceTab;
	}
	public String getCurrentPath() {
		return currentPath;
	}
	public FilesTabView getSourceTab() {
		return sourceTab;
	}
	
	
	
}
