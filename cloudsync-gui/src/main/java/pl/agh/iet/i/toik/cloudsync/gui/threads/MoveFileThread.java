package pl.agh.iet.i.toik.cloudsync.gui.threads;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.vaadin.ui.UI;

public class MoveFileThread extends AbstractUIThread<CloudFile> {

	private FilesTabView sourceTab;
	private FilesTabView destinationTab;
	private CloudFile sourceFile;

	public MoveFileThread(UI ui, CloseableProgressBar progressBar,
			CloudTask<CloudFile> cloudTask, FilesTabView sourceTab,
			CloudFile sourceFile, FilesTabView destinationTab) {
		super(ui, progressBar, cloudTask);
		this.sourceTab = sourceTab;
		this.destinationTab = destinationTab;
		this.sourceFile = sourceFile;
	}

	@Override
	protected void onFinish(CloudFile result) {
		sourceTab.deleteFile(sourceFile);
		destinationTab.addFile(result);
	}

}
