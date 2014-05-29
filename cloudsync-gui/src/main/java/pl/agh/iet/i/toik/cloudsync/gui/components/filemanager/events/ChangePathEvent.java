package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class ChangePathEvent {

	private FilesTabView sourceTab;
	private CloudFile destination;
	public ChangePathEvent(CloudFile destination , FilesTabView sourceTab) {
		this.destination = destination;
		this.sourceTab = sourceTab;
	}
	
	public CloudFile getDestination() {
		return destination;
	}

	public FilesTabView getSourceTab() {
		return sourceTab;
	}
	
	
	
}
