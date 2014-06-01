package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.WindowFooterLayout;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.LoginWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.LoginWindowView.LoginWindowViewPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudType;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
public class LoginWindow extends AbstractWindowView<LoginWindowViewPresenter>
		implements LoginWindowView {
	
	@Autowired
	private I18N captions;
	
	@Autowired
	private WindowFooterLayout windowFooterLayout;
	
	private CloudAccountContent cloudAccountContent;

	private Account account;
	
	@PostConstruct
	private void init() {
		windowFooterLayout.getCancelButton().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
				
			}
		});
		
		windowFooterLayout.getConfirmButton().addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String property = cloudAccountContent.getTokenPropertyId();
				String token = cloudAccountContent.getToken();
				getPresenter().login(account, property, token);
				close();
			}
		});
	}
	@Override
	public void showWindow(Account account) {
		this.account = account;
		cloudAccountContent.setCloudType((CloudType) account.getPropertyList().get("cloud.type"));
		cloudAccountContent.setAccountProperties(account.getPropertyList());
		super.showWindow();
	}

	@Override
	protected Component createWindowContent() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setSpacing(true);
		contentLayout.setMargin(true);
		contentLayout.addComponent(cloudAccountContent = new CloudAccountContent(CloudType.DROPBOX, true));
		contentLayout.addComponent(windowFooterLayout);
		windowFooterLayout.getConfirmButton().setCaption(captions.get("login.button"));
		return contentLayout;
	}

	@Override
	protected String getWindowCaption() {
		return captions.get("set.token");
	}

}
