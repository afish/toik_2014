package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerLayout;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerLayout.FileManagerLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
public class FileManagerLayoutPresenterImpl extends
		AbstractPresenter<FileManagerLayout> implements
		FileManagerLayoutPresenter {

	@Override
	public String getHelloMessage() {
		return "Welcome";
	}

}
