package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class AccountsWindowPresenterImpl extends
		AbstractPresenter<AccountsWindowView> implements
		AccountsWindowPresenter {
	
	private static Logger logger = LoggerFactory.getLogger(AccountsWindowPresenterImpl.class);
	
	private FilesTabSheetView currentFilesTabSheetView; 
	
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
	public void login(Collection<Account> accounts ) {
		logger.info("login");
		currentFilesTabSheetView.addNewTab(accounts);
		
	}

}
