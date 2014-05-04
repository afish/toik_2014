package pl.agh.iet.i.toik.cloudsync.gui.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerMainLayout;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

@VaadinView(name = "")
@UIScope
public class FileManagerView extends CustomComponent implements View {
	
	@Autowired
	private FileManagerMainLayout fileManagerLayout;
	
	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(fileManagerLayout);

	}

}
