package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import javax.annotation.PostConstruct;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
@UIScope
public class FileManagerFooterLayout extends VerticalLayout {

	
	@PostConstruct
	private void init(){
		addComponent(new Label("Footer"));
		setSizeFull();
	}
}
