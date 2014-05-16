package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerMiddleLayoutPresenterImpl extends AbstractPresenter<FileManagerMiddleLayoutView>
		implements FileManagerMiddleLayoutPresenter {
	
	@Autowired
	private FileManagerUpperLayoutPresenter upperLayoutPresenter;
	
	@Override
	public void registerDefaultFilesTabSheetView(FilesTabSheetView tabSheetView) {
		upperLayoutPresenter.setDefaultFilesTabSheetView(tabSheetView);		
	}
	
	@EventBusListenerMethod
	@Override
	public void onFileSelected(Event<FileSelectedEvent> event) {
		getComponentView().setCurrentTabSheet(event.getPayload().getTabSheetView());
		
	}
	
	

}
