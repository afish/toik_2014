package pl.agh.iet.i.toik.cloudsync.gui.threads;

import java.util.List;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.vaadin.ui.UI;

public class ChangePathThread extends AbstractUIThread<List<CloudFile>> {

	private FilesTabView tab;
	private CloudFile destination;
	
	

	public ChangePathThread(UI ui, CloseableProgressBar progressBar,
			CloudTask<List<CloudFile>> cloudTask, FilesTabView tab, CloudFile destination) {
		super(ui, progressBar, cloudTask);
		this.tab = tab;
		this.destination = destination;
	}



	@Override
	protected void finished(List<CloudFile> result) {
		tab.refresh(destination, result );
	}

}
