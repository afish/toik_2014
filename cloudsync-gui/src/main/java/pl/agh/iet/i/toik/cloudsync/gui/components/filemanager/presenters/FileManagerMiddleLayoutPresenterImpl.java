package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.ui.Notification;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.DeleteFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

@Component
public class FileManagerMiddleLayoutPresenterImpl extends AbstractPresenter<FileManagerMiddleLayoutView>
		implements FileManagerMiddleLayoutPresenter {
	
	@Autowired
	private FileManagerUpperLayoutPresenter upperLayoutPresenter;
	
	@Override
	public void registerDefaultFilesTabSheetView(FilesTabSheetView tabSheetView) {
		upperLayoutPresenter.setDefaultFilesTabSheetView(tabSheetView);	
		upperLayoutPresenter.setMiddleLayoutPresenter(this);
	}
	
	@EventBusListenerMethod
	@Override
	public void onFileSelected(Event<FileSelectedEvent> event) {
		FilesTabSheetView tabSheetView = event.getPayload().getTabSheetView();
		getComponentView().setCurrentTabSheet(tabSheetView);
		
	}

	@Override
	public void deleteAction() {
		FilesTabView sourceTab = getComponentView().getCurrentTabSheet().getCurrentTab();
		eventBus.publish(this, new DeleteFileEvent(sourceTab));
		
	}
	

}
