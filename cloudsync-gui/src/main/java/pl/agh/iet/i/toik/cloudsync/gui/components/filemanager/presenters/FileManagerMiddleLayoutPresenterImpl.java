package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerMiddleLayout.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerMiddleLayoutPresenterImpl extends AbstractPresenter<ComponentView<FileManagerMiddleLayoutPresenter>>
		implements FileManagerMiddleLayoutPresenter {
	
	@Autowired
	private FileManagerUpperLayoutPresenter upperLayoutPresenter;
	
	@Override
	public void registerDefaultFilesTabSheetView(FilesTabSheetView tabSheetView) {
		upperLayoutPresenter.setDefaultFilesTabSheetView(tabSheetView);		
	}

}
