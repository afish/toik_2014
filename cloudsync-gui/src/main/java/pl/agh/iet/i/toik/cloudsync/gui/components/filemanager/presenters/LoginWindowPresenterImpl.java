package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.OpenLoginWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.LoginWindowView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.LoginWindowView.LoginWindowViewPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudInformation;
import pl.agh.iet.i.toik.cloudsync.logic.CloudService;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;
import pl.agh.iet.i.toik.cloudsync.logic.CloudType;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

@Component
public class LoginWindowPresenterImpl extends
		AbstractPresenter<LoginWindowView> implements LoginWindowViewPresenter {
	
	@Autowired
	private CloudService cloudService;
	
	@Autowired
	private LogicService logicService;

	private FilesTabSheetView currentFilesTabSheetView;
	
	@Override
	public void login(Account account, String tokenPropertyId, String token) {
		
		account.getPropertyList().put(tokenPropertyId, token);
		CloudInformation cloudInformation = cloudService.getCloudByType((CloudType) account.getPropertyList().get("cloud.type"));
		CloudSession cloudSession = logicService.login(cloudInformation, account);
		currentFilesTabSheetView.addNewTab(cloudSession, account);

	}
	
	@EventBusListenerMethod
	@Override
	public void onOpenLoginWindow(Event<OpenLoginWindowEvent> event) {
		this.currentFilesTabSheetView = event.getPayload().getCurrentFilesTabSheetView();
		getComponentView().showWindow(event.getPayload().getAccount());
		
	}

}
