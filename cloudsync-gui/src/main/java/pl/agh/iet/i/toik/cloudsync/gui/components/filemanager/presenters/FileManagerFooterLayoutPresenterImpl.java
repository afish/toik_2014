package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.CopyFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.DeleteFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.MoveFileEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.threads.ChangePathThread;
import pl.agh.iet.i.toik.cloudsync.gui.threads.CopyFileThread;
import pl.agh.iet.i.toik.cloudsync.gui.threads.DeleteFileThread;
import pl.agh.iet.i.toik.cloudsync.gui.threads.MoveFileThread;
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
	
	Logger logger = LoggerFactory.getLogger(FileManagerFooterLayoutPresenterImpl.class);
			
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
		CloseableProgressBar progressBar = createProgresBar(createCaption(
				filesTabView.getAccount(), destination,
				captions.get("listing.files")));
		CloudTask<List<CloudFile>> cloudTask = logicService.listFiles(
				filesTabView.getCloudSession(), destination);
		executor.execute(new ChangePathThread(UI.getCurrent(), progressBar,
				cloudTask, filesTabView, destination));

	}

	@EventBusListenerMethod
	@Override
	public void onDeleteFile(Event<DeleteFileEvent> event) {
		FilesTabView filesTabView = event.getPayload().getSourceTab();
		if (filesTabView != null && filesTabView.getSelection() != null) {
			Account account = filesTabView.getAccount();
			CloudFile cloudFile = filesTabView.getSelection();
			CloseableProgressBar progressBar = createProgresBar(createCaption(
					account, cloudFile, captions.get("deleting.file")));
			CloudTask<Boolean> cloudTask = logicService.delete(
					filesTabView.getCloudSession(), cloudFile);
			executor.execute(new DeleteFileThread(UI.getCurrent(), progressBar,
					cloudTask, cloudFile, filesTabView));
		} else
			showEmptySelectionNotification();
	}

	private CloseableProgressBar createProgresBar(String caption) {
		return getComponentView().addProgressBar(caption);
	}

	private String createCaption(Account account, CloudFile destinantion,
			String msg) {
		StringBuilder stringBuilder = new StringBuilder(account.getName());
		stringBuilder.append(":");
		stringBuilder.append(msg);
		if (destinantion != null)
			stringBuilder.append(destinantion.getFullPath());
		else
			stringBuilder.append("/");
		return stringBuilder.toString();
	}

	private String createCaption(Account source, CloudFile sourceFile,
			String msg, Account dest, CloudFile destFile) {
		StringBuilder stringBuilder = new StringBuilder(source.getName());
		stringBuilder.append(":");
		stringBuilder.append(msg);
		if (sourceFile != null)
			stringBuilder.append(sourceFile.getFullPath());
		else
			stringBuilder.append("/");
		stringBuilder.append(" to ");
		stringBuilder.append(dest.getName());
		stringBuilder.append(":");
		if (destFile != null)
			stringBuilder.append(destFile.getFullPath());
		else
			stringBuilder.append("/");
		return stringBuilder.toString();
	}

	private void showEmptySelectionNotification() {
		Notification.show(captions.get("empty.selection.notification"),
				Notification.Type.WARNING_MESSAGE);
	}

	@EventBusListenerMethod
	@Override
	public void onCopyFile(Event<CopyFileEvent> event) {
		FilesTabView sourceTabView = event.getPayload().getSourceTab();
		FilesTabView destinationTabView = event.getPayload()
				.getDestinationTab();
		if (sourceTabView != null && sourceTabView.getSelection() != null
				&& destinationTabView != null) {
			Account account = sourceTabView.getAccount();
			CloudFile sourceFile = sourceTabView.getSelection();
			CloudFile destination = destinationTabView.getCurrentLocation();
			
			CloseableProgressBar progressBar = createProgresBar(createCaption(
					account, sourceFile, captions.get("copying.file"),
					destinationTabView.getAccount(), destination));
			
			CloudTask<CloudFile> cloudTask = logicService.copy(
					sourceTabView.getCloudSession(), sourceFile,
					destinationTabView.getCloudSession(), destination,
					sourceFile.getName());
			logger.info("Started copying thread");
			executor.execute(new CopyFileThread(UI.getCurrent(), progressBar,
					cloudTask, destinationTabView));
		} else
			showEmptySelectionNotification();
	}
	
	@EventBusListenerMethod
	@Override
	public void onMoveFile(Event<MoveFileEvent> event) {
		FilesTabView sourceTabView = event.getPayload().getSourceTab();
		FilesTabView destinationTabView = event.getPayload()
				.getDestinationTab();
		if (sourceTabView != null && sourceTabView.getSelection() != null
				&& destinationTabView != null) {
			Account account = sourceTabView.getAccount();
			CloudFile sourceFile = sourceTabView.getSelection();
			CloudFile destination = destinationTabView.getCurrentLocation();
			
			CloseableProgressBar progressBar = createProgresBar(createCaption(
					account, sourceFile, captions.get("moving.file"),
					destinationTabView.getAccount(), destination));
			
			CloudTask<CloudFile> cloudTask = logicService.move(
					sourceTabView.getCloudSession(), sourceFile,
					destinationTabView.getCloudSession(), destination,
					sourceFile.getName());
			logger.info("Started moving thread");
			executor.execute(new MoveFileThread(UI.getCurrent(), progressBar,
					cloudTask, sourceTabView, sourceFile, destinationTabView));
		} else
			showEmptySelectionNotification();
	}
}
