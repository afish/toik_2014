package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.WindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts.AddAccountWindow.AddAccountWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenAddWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.AccountMock;

@Component
public class AddAccountWindowPresenterImpl extends
		AbstractPresenter<WindowView<AddAccountWindowPresenter>> implements AddAccountWindowPresenter {

	@Override
	public void addAccount(AccountMock account) {
		eventBus.publish(this, account);

	}
	
	@EventBusListenerMethod
	@Override
	public void onOpenAddAccountWindow(Event<OpenAddWindowEvent> event) {
		getComponentView().showWindow();
		
	}

}
