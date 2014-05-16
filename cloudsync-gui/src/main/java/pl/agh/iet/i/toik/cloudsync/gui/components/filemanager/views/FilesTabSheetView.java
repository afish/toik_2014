package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import java.util.Collection;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.AccountMock;

public interface FilesTabSheetView extends ComponentView<FilesTabSheetPresenter> {

	public void addNewTab(Collection<AccountMock> accountsMock);
	
	public interface FilesTabSheetPresenter extends Presenter {
		
		public void openAccountsWindow();
		
		public void fileSelected();
		
		@EventBusListenerMethod
		public void onFileSelected(org.vaadin.spring.events.Event<FileSelectedEvent> event);

	}

	public void unselect();
}
