package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import java.util.Collection;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.FileMock;

public interface FilesTabView extends ComponentView<FilesTabSheetPresenter> {

	public FileMock getSelection();
	
	public void addFile(FileMock file);
	
	public void deleteFile(FileMock file);
	
	public void refresh(String path, Collection<FileMock> files);

	public void unselect();

}
