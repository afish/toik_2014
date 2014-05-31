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
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.DeleteFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.threads.ChangePathThread;
import pl.agh.iet.i.toik.cloudsync.gui.threads.DeleteFileThread;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;
import pl.agh.iet.i.toik.cloudsync.logic.LogicService;

import com.vaadin.ui.Notification;
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
		CloseableProgressBar progressBar = createProgresBar(createCaption(filesTabView.getAccount(), destination, captions.get("listing.files")));
		CloudTask<List<CloudFile>> cloudTask = logicService.listFiles(filesTabView.getCloudSession(),
				destination);
		executor.execute(new ChangePathThread(UI.getCurrent(), progressBar, cloudTask, filesTabView, destination));

	}
	
	@EventBusListenerMethod
	@Override
	public void onDeleteFile(Event<DeleteFileEvent> event) {
		FilesTabView filesTabView = event.getPayload().getSourceTab();
		if(filesTabView != null && filesTabView.getSelection() != null) {
			Account account = filesTabView.getAccount();
			CloudFile cloudFile = filesTabView.getSelection();
			CloseableProgressBar progressBar = createProgresBar(createCaption(account, cloudFile, captions.get("deleting.file")));
			CloudTask<Boolean> cloudTask = logicService.delete(filesTabView.getCloudSession(), cloudFile);
			executor.execute(new DeleteFileThread(UI.getCurrent(), progressBar, cloudTask, cloudFile, filesTabView));
		}
		else
			showEmptySelectionNotification();
	}
	
	private CloseableProgressBar createProgresBar(String caption) {
		return  getComponentView().addProgressBar(caption);
	}
	private String createCaption(Account account, CloudFile destinantion, String msg) {
		StringBuilder stringBuilder = new StringBuilder(account.getName());
		stringBuilder.append(":");
		stringBuilder.append(msg);
		if(destinantion != null)
			stringBuilder.append(destinantion.getFullPath());
		else
			stringBuilder.append("/");
		return stringBuilder.toString();
	}
	
	private void showEmptySelectionNotification() {
		Notification.show(
				captions.get("empty.selection.notification"),
				Notification.Type.WARNING_MESSAGE);
	}

	

}
