package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Collection;
import java.util.Date;

import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.model.FileMock;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class FilesTab extends VerticalLayout implements FilesTabView {

	private BeanItemTable<FileMock> filesTable;
	private Button changePathButton;
	private FilesTabSheetPresenter presenter;
	private TextField pathField;

	public FilesTab(FilesTabSheetPresenter tabSheetPresenter) {
		this.presenter = tabSheetPresenter;
		setComponents();
		setListeners();
		filesTable.addItem(new FileMock("path", "DIR", "N/A", new Date()));
		filesTable.addItem(new FileMock("path2", "DIR", "N/A", new Date()));

	}

	private void setListeners() {
		
		filesTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				getPresenter().fileSelected();
			}
		});
		
		changePathButton.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().changePath(pathField.getValue(),(FilesTabView)FilesTab.this);
			}
		});
		
	}

	private void setComponents() {
		Label pathLabel = new Label("Path:");
		pathLabel.addStyleName(Reindeer.LABEL_H2);
		pathLabel.addStyleName("greyBackground");
		pathLabel.setSizeUndefined();

		pathField = new TextField();
		pathField.setImmediate(true);
		pathField.setWidth(100, Unit.PERCENTAGE);
		pathField.setValue("/");
		changePathButton = createButtonWithIcon("play.png");

		HorizontalLayout pathLayout = new HorizontalLayout();
		pathLayout.setWidth(100, Unit.PERCENTAGE);
		pathLayout.addComponent(pathLabel);
		pathLayout.addComponent(pathField);
		pathLayout.addComponent(changePathButton);
		pathLayout.setExpandRatio(pathField, 3);

		addComponent(pathLayout);
		addComponent(filesTable = new BeanItemTable<FileMock>(FileMock.class));
		setExpandRatio(filesTable, 2);
		setSizeFull();
	}

	private Button createButtonWithIcon(String string) {
		Button btn = new Button();
		btn.setStyleName(Reindeer.BUTTON_LINK);
		btn.setIcon(new ThemeResource(string));
		btn.setSizeUndefined();
		return btn;
	}

	@Override
	public void setPresenter(FilesTabSheetPresenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public FilesTabSheetPresenter getPresenter() {
		return presenter;
	}

	@Override
	public Collection<FileMock> getSelection() {

		return (Collection<FileMock>) filesTable.getValue();
	}

	@Override
	public void addFiles(Collection<FileMock> files) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFiles(Collection<FileMock> files) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(String path, Collection<FileMock> files) {
		pathField.setValue(path);
		filesTable.removeAllItems();
		for(FileMock file : files)
			filesTable.addItem(file);
		
	}

	@Override
	public void unselect() {
		filesTable.setValue(null);
	}
}
