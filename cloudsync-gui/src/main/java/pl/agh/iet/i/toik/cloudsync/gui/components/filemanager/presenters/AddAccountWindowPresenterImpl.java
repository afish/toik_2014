package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts.AddAccountWindow.AddAccountWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.AddAccountEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.AccountService;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;
import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

@Component
public class AddAccountWindowPresenterImpl extends
		AbstractPresenter<WindowView<AddAccountWindowPresenter>> implements AddAccountWindowPresenter {

	private static Logger logger = LoggerFactory.getLogger(AddAccountWindowPresenterImpl.class);
	
	@Autowired
	private CloudService cloudService;
	
	@Autowired
	private AccountService accountService;
	
	
	@EventBusListenerMethod
	@Override
	public void onOpenAddAccountWindow(Event<OpenAddWindowEvent> event) {
		logger.info("onOpenAddAccountWindow: " + event.getPayload());
		getComponentView().showWindow();
		
	}

	@Override
	public List<CloudInformation> getAllClouds() {
		return cloudService.getAllClouds();
	}

	@Override
	public void addAccount(String name, CloudInformation cloudInformation,
			Map<String, Object> properties) {
		Account account = new Account(cloudInformation.getId()+ name, name, properties);
		accountService.saveAccount(account);
		logger.info("addAccount: " + account);
		eventBus.publish(this, new AddAccountEvent(account, cloudInformation));
		
	}

}
