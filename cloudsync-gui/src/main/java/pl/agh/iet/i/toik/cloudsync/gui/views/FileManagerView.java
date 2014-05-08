package pl.agh.iet.i.toik.cloudsync.gui.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerMainPanel;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@VaadinView(name = "")
@UIScope
public class FileManagerView extends VerticalLayout implements View {
	
	@Autowired
	private FileManagerMainPanel fileManagerLayout;
	
	@Override
	public void enter(ViewChangeEvent event) {
		fileManagerLayout.setSizeFull();
		addComponent(fileManagerLayout);
		setSizeFull();
	}

}
