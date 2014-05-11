package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.AccountsWindowView.AccountsWindowPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.AccountMock;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
public class AccountsWindow extends AbstractWindowView<AccountsWindowPresenter>
		implements AccountsWindowView {

	@Autowired
	private I18N captions;

	private Button loginButton;

	private Button addButton;

	private Button deleteButton;

	private Button cancelButton;

	private BeanItemTable<AccountMock> accountTable;

	@PostConstruct
	private void init() {
		setWidth(40, Unit.PERCENTAGE);
		setHeight(40, Unit.PERCENTAGE);
		setListeners();
	}

	private void setListeners() {
		cancelButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				close();

			}
		});

		addButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().openAddWindow();

			}
		});

		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				Collection<AccountMock> accounts = (Collection<AccountMock>) accountTable.getValue();
				if (accounts != null && accounts.size() > 0) {
					getPresenter().login(accounts);
					close();
				}else
					Notification.show(
							captions.get("empty.selection.notification"),
							Notification.Type.WARNING_MESSAGE);

			}
		});
	}

	@Override
	protected Component createWindowContent() {
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(true);
		mainLayout.addComponent(accountTable = new BeanItemTable<AccountMock>(
				AccountMock.class));
		mainLayout.addComponent(createButtonLayout());
		mainLayout.setSizeFull();
		return mainLayout;
	}

	private Component createButtonLayout() {
		VerticalLayout buttonLayout = new VerticalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(new MarginInfo(true, false, false, false));
		buttonLayout.setSizeFull();
		buttonLayout.addComponent(loginButton = new Button(captions
				.get("login.button")));
		buttonLayout.addComponent(addButton = new Button(captions
				.get("add.button")));
		buttonLayout.addComponent(deleteButton = new Button(captions
				.get("delete.button")));
		buttonLayout.addComponent(cancelButton = new Button(captions
				.get("cancel.button")));
		loginButton.setWidth(65, Unit.PIXELS);
		addButton.setWidth(65, Unit.PIXELS);
		deleteButton.setWidth(65, Unit.PIXELS);
		cancelButton.setSizeUndefined();
		buttonLayout
				.setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
		return buttonLayout;
	}

	@Override
	protected String getWindowCaption() {
		return captions.get("accounts.window");
	}

	@Override
	public void addAccount(AccountMock account) {
		accountTable.addItem(account);

	}

}
