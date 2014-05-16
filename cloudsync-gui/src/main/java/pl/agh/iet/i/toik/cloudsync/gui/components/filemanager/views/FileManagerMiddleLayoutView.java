package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

public interface FileManagerMiddleLayoutView extends ComponentView<FileManagerMiddleLayoutPresenter> {

	public interface FileManagerMiddleLayoutPresenter extends Presenter {
		public void registerDefaultFilesTabSheetView(
				FilesTabSheetView tabSheetView);
		
		@EventBusListenerMethod
		public void onFileSelected(org.vaadin.spring.events.Event<FileSelectedEvent> event);
	}
	

	void setCurrentTabSheet(FilesTabSheetView tabSheetView);
	FilesTabSheetView getCurrentTabSheet();
	
}
