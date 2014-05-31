package pl.agh.iet.i.toik.cloudsync.gui.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.vaadin.ui.UI;

public class CopyFileThread extends AbstractUIThread<CloudFile> {
	
	Logger logger = LoggerFactory.getLogger(CopyFileThread.class);
	
	private FilesTabView destinationTab;

	public CopyFileThread(UI ui, CloseableProgressBar progressBar,
			CloudTask<CloudFile> cloudTask, FilesTabView destinationTab) {
		super(ui, progressBar, cloudTask);
		this.destinationTab = destinationTab;
		setName("Copying thread");
	}

	@Override
	protected void onFinish(CloudFile result) {
		destinationTab.addFile(result);
		logger.info("Copying file finished");
	}

}
