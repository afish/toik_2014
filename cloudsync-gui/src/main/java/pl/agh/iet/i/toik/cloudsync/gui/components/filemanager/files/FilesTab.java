package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Collection;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilesTab extends VerticalLayout implements FilesTabView {

	private FilesTable filesTable;
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


	}

	private void setComponents() {
	

		pathField = new TextField();
		pathField.setImmediate(true);
		pathField.setWidth(100, Unit.PERCENTAGE);
		pathField.setValue("/");
		pathField.setReadOnly(true);

		HorizontalLayout pathLayout = new HorizontalLayout();
		pathLayout.setWidth(100, Unit.PERCENTAGE);
		pathLayout.addComponent(pathField);

		addComponent(pathLayout);
		addComponent(filesTable = new FilesTable());
		setExpandRatio(filesTable, 2);
		setSizeFull();
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
		if(destination != null) {
			pathField.setReadOnly(false);
			pathField.setValue(destination.getFullPath());
			pathField.setReadOnly(true);
		}
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
