package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Collection;

import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

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

	private BeanItemTable<CloudFile> filesTable;
	private Button changePathButton;
	private FilesTabSheetPresenter presenter;
	private TextField pathField;
	private CloudSession cloudSession;
	private Account account;

	public FilesTab(FilesTabSheetPresenter tabSheetPresenter, Account account,
			CloudSession cloudSession) {
		this.presenter = tabSheetPresenter;
		this.account = account;
		this.cloudSession = cloudSession;
		setComponents();
		setListeners();
		setItems();

	}

	private void setItems() {
		getPresenter().changePath(null, this);

	}

	private void setListeners() {

		filesTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				getPresenter().fileSelected();
			}
		});

		changePathButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
		
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
		addComponent(filesTable = new BeanItemTable<CloudFile>(CloudFile.class));
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
	public CloudFile getSelection() {

		return (CloudFile) filesTable.getValue();
	}

	@Override
	public void refresh(CloudFile destination, Collection<CloudFile> files) {
		if(destination != null)
			pathField.setValue(destination.getFullPath());
		filesTable.removeAllItems();
		for (CloudFile file : files)
			filesTable.addItem(file);

	}

	@Override
	public void unselect() {
		filesTable.setValue(null);
	}

	@Override
	public void addFile(CloudFile file) {
		filesTable.addItem(file);

	}

	@Override
	public void deleteFile(CloudFile file) {
		filesTable.removeItem(file);

	}

	public CloudSession getCloudSession() {
		return cloudSession;
	}

	public Account getAccount() {
		return account;
	}

}
