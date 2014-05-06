package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.events.EventBusScope;
import org.vaadin.spring.events.EventScope;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerMainLayoutPresenterImpl extends
		AbstractPresenter<FileManagerMainLayoutView> implements
		FileManagerMainLayoutView.FileManagerMainLayoutPresenter {
	
	@Autowired
	@EventBusScope(EventScope.APPLICATION)
	private EventBus eventBus;
	
	@PostConstruct
	private void init(){
		eventBus.subscribe(this);
	}
	
	@PreDestroy
	private void destroy(){
		eventBus.unsubscribe(this);
	}
	
	@Override
	@EventBusListenerMethod
	public void onDeleteAction(Event<String> event) {
		getComponentView().addMessage(event.getPayload());

	}

}
