package pl.agh.iet.i.toik.cloudsync.gui.filemanager.presenters.impl;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.filemanager.presenters.FileManagerLayoutPresenter;

@Component
public class FileManagerLayoutPresenterImpl implements FileManagerLayoutPresenter{

	@Override
	public String getHelloMessage() {
		return "Welcome";
	}

}
