package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.threads.ChangePathThread;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

import com.vaadin.ui.UI;

@Component
public class FileManagerFooterLayoutPresenterImpl extends
		AbstractPresenter<FileManagerFooterLayoutView> implements
		FileManagerFooterLayoutPresenter {
	
	@Autowired
	private I18N captions;
	
	@Autowired
	private ThreadPoolTaskExecutor executor;
	
	@Autowired
	private LogicService logicService;

	@EventBusListenerMethod
	@Override
	public void onChangePath(Event<ChangePathEvent> event) {	
		FilesTabView filesTabView = event.getPayload().getSourceTab();
		CloudFile destination = event.getPayload().getDestination();
		CloseableProgressBar progressBar = getComponentView().addProgressBar(createCaption(filesTabView.getAccount(), destination));
		CloudTask<List<CloudFile>> cloudTask = logicService.listFiles(filesTabView.getCloudSession(),
				destination);
		executor.execute(new ChangePathThread(UI.getCurrent(), progressBar, cloudTask, filesTabView, destination));

	}
	
	private String createCaption(Account account, CloudFile destinantion) {
		StringBuilder stringBuilder = new StringBuilder(account.getName());
		stringBuilder.append(":");
		stringBuilder.append(captions.get("listing.files"));
		if(destinantion != null)
			stringBuilder.append(destinantion.getFullPath());
		else
			stringBuilder.append("/");
		return stringBuilder.toString();
	}

}
