package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import java.util.Collection;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView.AccountsWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

public interface AccountsWindowView extends WindowView<AccountsWindowPresenter> {
	
	public void addAccount(Account account);
	
	public interface AccountsWindowPresenter extends Presenter {
		
		@EventBusListenerMethod
		public void onOpenWindow(org.vaadin.spring.events.Event<OpenAccountsWindowEvent> event);
		
		public void openAddWindow();
		
		@EventBusListenerMethod
		public void onNewAccountAdded(org.vaadin.spring.events.Event<Account> event);
	
		public void login(Account account);
		
		public Collection<Account> getAccounts();

		public void deleteAccount(Account account);
	}
}
