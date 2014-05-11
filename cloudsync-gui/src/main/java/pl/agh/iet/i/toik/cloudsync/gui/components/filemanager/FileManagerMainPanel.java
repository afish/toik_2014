package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMainLayoutView.FileManagerMainLayoutPresenter;

import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

@VaadinComponent
@UIScope
public class FileManagerMainPanel extends
		AbstractComponentView<Panel, FileManagerMainLayoutPresenter> implements
		FileManagerMainLayoutView {

	private VerticalSplitPanel splitPanel;

	@Autowired
	private FileManagerUpperLayout fileManagerUpperLayout;

	@Autowired
	private FileManagerMiddleLayout fileManagerMiddleLayout;

	@Autowired
	private FileManagerFooterLayout fileManagerFooterLayout;

	private VerticalLayout contentLayout;

	@Override
	protected Panel createContent() {

		contentLayout = new VerticalLayout();
		contentLayout.setSizeFull();
		contentLayout.addComponent(new Panel(fileManagerUpperLayout));
		Panel middlePanel ;
		contentLayout.addComponent(middlePanel = createPanel(createSplitPanel()));
		contentLayout.setExpandRatio(middlePanel, 2);
		return createPanel(contentLayout);
	}

	private Panel createPanel(Component content) {
		Panel panel = new Panel(content);
		panel.setSizeFull();
		return panel;
	}

	private VerticalSplitPanel createSplitPanel() {
		splitPanel = new VerticalSplitPanel();
		splitPanel.setSplitPosition(70, Unit.PERCENTAGE);
		splitPanel.setMinSplitPosition(20, Unit.PERCENTAGE);
		splitPanel.setFirstComponent(fileManagerMiddleLayout);
		splitPanel.setSecondComponent(fileManagerFooterLayout);
		return splitPanel;
	}
}
