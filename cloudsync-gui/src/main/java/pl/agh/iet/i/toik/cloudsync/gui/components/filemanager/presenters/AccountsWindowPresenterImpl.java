package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.AddAccountEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenLoginWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView.AccountsWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.AccountService;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;
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
	
	@Autowired
	private CloudService cloudService;

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
	public void onNewAccountAdded(Event<AddAccountEvent> event) {
		Account account = event.getPayload().getAccount();
		logger.info("onNewAccountAdded: " + account);
		getComponentView().addAccount(account);
		
	}

	@Override
	public void login(Account account ) {
		logger.info("login");
		eventBus.publish(this, new OpenLoginWindowEvent(account, currentFilesTabSheetView));
		
	}

	@Override
	public Collection<Account> getAccounts() {
		logger.info("Getting accounts");
		return accountService.getAllAccounts();
	}

	@Override
	public void deleteAccount(Account account) {
		accountService.removeAccount(account);
		
	}

}
