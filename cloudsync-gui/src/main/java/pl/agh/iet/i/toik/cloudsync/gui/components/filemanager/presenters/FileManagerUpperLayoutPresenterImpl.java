package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusScope;
import org.vaadin.spring.events.EventScope;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerUpperLayoutPresenterImpl extends AbstractPresenter<FileManagerUpperLayout> implements
		FileManagerUpperLayoutPresenter {

	
	@Autowired
	@EventBusScope(EventScope.APPLICATION)
	private EventBus eventBus;
	
	@Override
	public void deleteAction() {
		eventBus.publish(this, "DELETE ACTION");

	}

}
