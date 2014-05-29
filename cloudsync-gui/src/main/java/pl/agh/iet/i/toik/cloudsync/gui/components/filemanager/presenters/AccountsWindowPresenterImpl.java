package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView.AccountsWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.AccountService;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

@Component
public class AccountsWindowPresenterImpl extends
		AbstractPresenter<AccountsWindowView> implements
		AccountsWindowPresenter {
	
	private static Logger logger = LoggerFactory.getLogger(AccountsWindowPresenterImpl.class);
	
	private FilesTabSheetView currentFilesTabSheetView; 
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private LogicService logicService;

	
	@Override
	@EventBusListenerMethod
	public void onOpenWindow(Event<OpenAccountsWindowEvent> event) {
		logger.info("onOpenWindow");
		currentFilesTabSheetView = event.getPayload().getCaller();		
		getComponentView().showWindow();

	}

	@Override
	public void openAddWindow() {
		logger.info("openAddWindow");
		eventBus.publish(this, new OpenAddWindowEvent());
		
	}
	
	@EventBusListenerMethod
	@Override
	public void onNewAccountAdded(Event<Account> event) {
		logger.info("onNewAccountAdded: " + event.getPayload());
		getComponentView().addAccount(event.getPayload());
		
	}

	@Override
	public void login(Account account ) {
		logger.info("login");
		CloudInformation cloudInformation = (CloudInformation) account.getPropertyList().get("cloudInformation");
		CloudSession cloudSession = logicService.login(cloudInformation, account);
		currentFilesTabSheetView.addNewTab(cloudSession, account);
		
	}

	@Override
	public Collection<Account> getAccounts() {
		logger.info("Getting accounts");
		return accountService.getAllAccounts();
	}

}
