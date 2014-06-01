package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views;

import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenLoginWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.LoginWindowView.LoginWindowViewPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

public interface LoginWindowView extends WindowView<LoginWindowViewPresenter> {
	
	public void showWindow(Account account);
	
	public interface LoginWindowViewPresenter extends Presenter {
		public void login(Account account, String tokenPropertyId, String token);
		
		@EventBusListenerMethod
		public void onOpenLoginWindow(org.vaadin.spring.events.Event<OpenLoginWindowEvent> event);
	}
}
