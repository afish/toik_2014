package pl.agh.iet.i.toik.cloudsync.gui.filemanager.presenters;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.filemanager.components.FileManagerLayout;
import pl.agh.iet.i.toik.cloudsync.gui.filemanager.components.FileManagerLayout.FileManagerLayoutPresenter;

@Component
public class FileManagerLayoutPresenterImpl extends
		AbstractPresenter<FileManagerLayout> implements
		FileManagerLayoutPresenter {

	@Override
	public String getHelloMessage() {
		return "Welcome";
	}

}
