package pl.agh.iet.i.toik.cloudsync.gui.components.presenters;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventBusScope;
import org.vaadin.spring.events.EventScope;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;

public abstract class AbstractPresenter<VIEW extends ComponentView<? extends Presenter>>
		implements Presenter {

	private VIEW view;
	
	@Autowired
	@EventBusScope(EventScope.APPLICATION)
	protected EventBus eventBus;
	
	@PostConstruct
	private void init(){
		eventBus.subscribe(this, true);
	}
	
	@PreDestroy
	private void destroy(){
		eventBus.unsubscribe(this);
	}
	
	@Override
	public void setComponentView(ComponentView<? extends Presenter> view) {
		this.view = (VIEW) view;

	}

	@Override
	public VIEW getComponentView() {
		return view;
	}

}
