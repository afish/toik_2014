package pl.agh.iet.i.toik.cloudsync.gui.filemanager.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.filemanager.presenters.FileManagerLayoutPresenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
@UIScope
public class FileManagerLayout extends VerticalLayout {
	
	@Autowired
	public FileManagerLayout(FileManagerLayoutPresenter presenter) {
		setSizeFull();
		addComponent(new Label(presenter.getHelloMessage()));
	}
}
