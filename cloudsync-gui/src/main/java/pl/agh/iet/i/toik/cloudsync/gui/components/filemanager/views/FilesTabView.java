package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import java.util.Collection;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

public interface FilesTabView extends ComponentView<FilesTabSheetPresenter> {

	public CloudFile getSelection();
	
	public void addFile(CloudFile file);
	
	public void deleteFile(CloudFile file);
	
	public void refresh(CloudFile destination, Collection<CloudFile> files);

	public void unselect();
	
	public CloudSession getCloudSession();

	public Account getAccount() ;

	public CloudFile getCurrentLocation();

}
