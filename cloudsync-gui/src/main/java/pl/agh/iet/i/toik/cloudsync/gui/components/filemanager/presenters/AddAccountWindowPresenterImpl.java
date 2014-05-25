package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts.AddAccountWindow.AddAccountWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

@Component
public class AddAccountWindowPresenterImpl extends
		AbstractPresenter<WindowView<AddAccountWindowPresenter>> implements AddAccountWindowPresenter {

	private static Logger logger = LoggerFactory.getLogger(AddAccountWindowPresenterImpl.class);
	
	@Override
	public void addAccount(Account account) {
		logger.info("addAccount: " + account);
		eventBus.publish(this, account);

	}
	
	@EventBusListenerMethod
	@Override
	public void onOpenAddAccountWindow(Event<OpenAddWindowEvent> event) {
		logger.info("onOpenAddAccountWindow: " + event.getPayload());
		getComponentView().showWindow();
		
	}

}
