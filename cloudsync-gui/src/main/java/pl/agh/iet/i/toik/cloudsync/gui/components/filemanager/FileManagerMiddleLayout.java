package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import javax.annotation.PostConstruct;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;

@VaadinComponent
@UIScope
public class FileManagerMiddleLayout extends HorizontalSplitPanel {

	
	@PostConstruct
	private void init(){
		setSplitPosition(50, Unit.PERCENTAGE);
		setSizeFull();
		setFirstComponent(new Label("LEFT"));
		setSecondComponent(new Label("RIGHT"));
	}
}
