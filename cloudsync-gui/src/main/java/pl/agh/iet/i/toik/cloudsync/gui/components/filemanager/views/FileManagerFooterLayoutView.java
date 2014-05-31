package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.CopyFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.DeleteFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.MoveFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

public interface FileManagerFooterLayoutView extends ComponentView<FileManagerFooterLayoutPresenter> {

	
	CloseableProgressBar addProgressBar(String caption);
	
	public interface FileManagerFooterLayoutPresenter extends Presenter {
		
		@EventBusListenerMethod
		public void onChangePath(org.vaadin.spring.events.Event<ChangePathEvent> event);
		
		@EventBusListenerMethod
		public void onDeleteFile(org.vaadin.spring.events.Event<DeleteFileEvent> event);
		
		@EventBusListenerMethod
		public void onCopyFile(org.vaadin.spring.events.Event<CopyFileEvent> event);
		
		@EventBusListenerMethod
		public void onMoveFile(org.vaadin.spring.events.Event<MoveFileEvent> event);
	}
}
