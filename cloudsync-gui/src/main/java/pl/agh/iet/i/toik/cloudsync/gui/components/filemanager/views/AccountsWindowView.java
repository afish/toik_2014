package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import java.util.Collection;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView.AccountsWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.AccountMock;

public interface AccountsWindowView extends WindowView<AccountsWindowPresenter> {
	
	
	public void addAccount(AccountMock account);
	
	public interface AccountsWindowPresenter extends Presenter {
		
		@EventBusListenerMethod
		public void onOpenWindow(org.vaadin.spring.events.Event<OpenAccountsWindowEvent> event);
		
		public void openAddWindow();
		
		@EventBusListenerMethod
		public void onNewAccountAdded(org.vaadin.spring.events.Event<AccountMock> event);
	
		public void login(Collection<AccountMock> accountsMock);
	}
}
