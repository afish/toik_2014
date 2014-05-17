package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.FileSelectedEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
@Scope("prototype")
public class FilesTabSheetPresenterImpl extends AbstractPresenter<FilesTabSheetView> implements
		FilesTabSheetPresenter {

	@Override
	public void openAccountsWindow() {
		eventBus.publish(this, new OpenAccountsWindowEvent(getComponentView()));
	}

	@Override
	public void fileSelected() {
		eventBus.publish(this, new FileSelectedEvent(getComponentView()));
		
	}
	
	@EventBusListenerMethod
	@Override
	public void onFileSelected(Event<FileSelectedEvent> event) {
		if(event.getPayload().getTabSheetView() != getComponentView()) {
			getComponentView().unselect();
			
		}
	
	}

	@Override
	public void changePath(String path, FilesTabView filesTabView) {
		eventBus.publish(this, new ChangePathEvent(path, filesTabView));
		
	}



}
