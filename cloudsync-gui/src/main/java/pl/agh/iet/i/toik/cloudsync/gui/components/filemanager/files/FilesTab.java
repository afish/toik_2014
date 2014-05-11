package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.gui.model.FileMock;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class FilesTab extends VerticalLayout {

	private BeanItemTable<FileMock> table;

	public FilesTab(){
		Label pathLabel = new Label("Path:");
		pathLabel.addStyleName(Reindeer.LABEL_H2);
		pathLabel.addStyleName("greyBackground");
		pathLabel.setSizeUndefined();
		TextField pathField = new TextField();
		pathField.setImmediate(true);
		pathField.setWidth(100, Unit.PERCENTAGE);
		Button goButton = createButton("play.png");
		HorizontalLayout pathLayout = new HorizontalLayout();
		pathLayout.setWidth(100,Unit.PERCENTAGE);
		pathLayout.addComponent(pathLabel);
		pathLayout.addComponent(pathField);
		pathLayout.addComponent(goButton);
		pathLayout.setExpandRatio(pathField, 3);
		addComponent(pathLayout);
		addComponent(table = new BeanItemTable<FileMock>(FileMock.class));
		setExpandRatio(table, 2);
		setSizeFull();
	}

	private Button createButton(String string) {
		Button btn = new Button();
		btn.setStyleName(Reindeer.BUTTON_LINK);
		btn.setIcon(new ThemeResource(string));
		btn.setSizeUndefined();
		return btn;
	}
}
