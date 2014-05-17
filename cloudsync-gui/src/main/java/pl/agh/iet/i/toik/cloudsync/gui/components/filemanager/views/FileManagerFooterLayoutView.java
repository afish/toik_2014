package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

public interface FileManagerFooterLayoutView extends ComponentView<FileManagerFooterLayoutPresenter> {

	
	CloseableProgressBar addProgressBar(String caption);
	
	public interface FileManagerFooterLayoutPresenter extends Presenter {
		
		@EventBusListenerMethod
		public void onChangePath(org.vaadin.spring.events.Event<ChangePathEvent> event);
	}
}
