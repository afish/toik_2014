package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.Event;
import org.vaadin.spring.events.EventBusListenerMethod;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.events.ChangePathEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.FileMock;
import pl.agh.iet.i.toik.cloudsync.gui.threads.ChangePathThread;

import com.vaadin.ui.UI;

@Component
public class FileManagerFooterLayoutPresenterImpl extends
		AbstractPresenter<FileManagerFooterLayoutView> implements
		FileManagerFooterLayoutPresenter {

	@Autowired
	private ThreadPoolTaskExecutor executor;

	@EventBusListenerMethod
	@Override
	public void onChangePath(Event<ChangePathEvent> event) {
		CloseableProgressBar progressBar = getComponentView().addProgressBar("New path");
		executor.execute(new ChangePathThread(UI.getCurrent(), progressBar,
				new FileMock("new path", "jpg", "100", new Date()), event.getPayload().getSourceTab()));

	}

}
