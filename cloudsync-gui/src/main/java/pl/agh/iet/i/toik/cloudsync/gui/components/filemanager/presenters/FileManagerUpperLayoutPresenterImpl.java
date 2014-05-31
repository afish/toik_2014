package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerUpperLayoutPresenterImpl extends AbstractPresenter<FileManagerUpperLayout> implements
		FileManagerUpperLayoutPresenter {

	private FilesTabSheetView defaultFileTabSheetView;
	private FilesTabSheetView currentTabSheet;
	private FileManagerMiddleLayoutPresenter middleLayoutPresenter;

	@Override
	public void openAccountsWindow() {
			eventBus.publish(this, new OpenAccountsWindowEvent(defaultFileTabSheetView));
		
	}

	@Override
	public void setDefaultFilesTabSheetView(FilesTabSheetView tabSheetView) {
		this.defaultFileTabSheetView = tabSheetView;
		
	}

	@Override
	public void deleteAction() {
		middleLayoutPresenter.deleteAction();
	}

	@Override
	public void setMiddleLayoutPresenter(
			FileManagerMiddleLayoutPresenter fileManagerMiddleLayoutPresenterImpl) {
		this.middleLayoutPresenter = fileManagerMiddleLayoutPresenterImpl;
		
	}

	

}
