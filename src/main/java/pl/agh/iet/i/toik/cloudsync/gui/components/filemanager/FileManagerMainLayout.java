package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView.FileManagerMainLayoutPresenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
@UIScope
public class FileManagerMainLayout extends
		AbstractComponentView<Panel, FileManagerMainLayoutPresenter> implements
		FileManagerMainLayoutView {

	@Autowired
	private FileManagerUpperLayout fileManagerUpperLayout;
	
	private VerticalLayout contentLayout;

	@Override
	protected Panel createContent() {
		Panel content = new Panel();
		contentLayout = new VerticalLayout();
		contentLayout.addComponent(fileManagerUpperLayout);
		contentLayout.setSizeFull();
		content.setContent(contentLayout);
		return content;
	}

	@Override
	public void addMessage(String message) {
		contentLayout.addComponent(new Label(message));
		
	}
}
