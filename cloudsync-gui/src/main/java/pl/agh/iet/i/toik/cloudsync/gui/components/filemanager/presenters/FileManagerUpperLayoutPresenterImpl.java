package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerUpperLayoutPresenterImpl extends AbstractPresenter<FileManagerUpperLayout> implements
		FileManagerUpperLayoutPresenter {

	private FilesTabSheetView defaultFileTabSheetView;

	@Override
	public void openAccountsWindow() {
			eventBus.publish(this, new OpenAccountsWindowEvent(defaultFileTabSheetView));
		
	}

	@Override
	public void setDefaultFilesTabSheetView(FilesTabSheetView tabSheetView) {
		this.defaultFileTabSheetView = tabSheetView;
		
	}

	

}
