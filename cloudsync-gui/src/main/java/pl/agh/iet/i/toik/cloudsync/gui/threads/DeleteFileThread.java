package pl.agh.iet.i.toik.cloudsync.gui.threads;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class DeleteFileThread extends AbstractUIThread<Boolean> {

	private CloudFile fileToDelete;
	private FilesTabView sourceTab;

	public DeleteFileThread(UI ui, CloseableProgressBar progressBar,
			CloudTask<Boolean> cloudTask, CloudFile fileToDelete, FilesTabView sourceTab) {
		super(ui, progressBar, cloudTask);
		this.fileToDelete = fileToDelete;
		this.sourceTab = sourceTab;
	}

	@Override
	protected void finished(Boolean result) {
		if(result.booleanValue())
			sourceTab.deleteFile(fileToDelete);
		else
			Notification.show("Could not delete file: "+fileToDelete.getName());
	}

}
