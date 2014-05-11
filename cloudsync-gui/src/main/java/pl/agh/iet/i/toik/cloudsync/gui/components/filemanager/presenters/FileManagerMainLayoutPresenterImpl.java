package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerMainLayoutPresenterImpl extends
		AbstractPresenter<FileManagerMainLayoutView> implements
		FileManagerMainLayoutView.FileManagerMainLayoutPresenter {
	
	
	@Override
	@EventBusListenerMethod
	public void onDeleteAction(Event<String> event) {

	}

}
