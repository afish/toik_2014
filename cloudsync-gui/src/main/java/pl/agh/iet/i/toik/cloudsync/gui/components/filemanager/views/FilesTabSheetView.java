package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

public interface FilesTabSheetView extends ComponentView<FilesTabSheetPresenter> {

	public void addNewTab(CloudSession cloudSession, Account account);
	public void unselect();
	public FilesTabView getCurrentTab();
	
	public interface FilesTabSheetPresenter extends Presenter {
		
		public void openAccountsWindow();
		
		public void fileSelected();
		
		@EventBusListenerMethod
		public void onFileSelected(org.vaadin.spring.events.Event<FileSelectedEvent> event);

		public void changePath(CloudFile destinantion,  FilesTabView filesTabView);

	}




}
