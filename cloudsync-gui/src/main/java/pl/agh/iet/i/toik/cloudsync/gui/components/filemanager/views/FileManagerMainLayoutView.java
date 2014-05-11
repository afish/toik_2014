package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView.FileManagerMainLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

public interface FileManagerMainLayoutView extends ComponentView<FileManagerMainLayoutPresenter> {
	

	public interface FileManagerMainLayoutPresenter extends Presenter {
		
		@EventBusListenerMethod
		void onDeleteAction(org.vaadin.spring.events.Event<String> event);
	}
}
