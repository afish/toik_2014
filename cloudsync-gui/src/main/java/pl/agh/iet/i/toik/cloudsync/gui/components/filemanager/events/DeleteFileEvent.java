package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;

public class DeleteFileEvent {

	private FilesTabView sourceTab;

	public DeleteFileEvent(FilesTabView sourceTab) {
		this.sourceTab = sourceTab;

	}

	public FilesTabView getSourceTab() {
		return sourceTab;
	}
	
	 
}
