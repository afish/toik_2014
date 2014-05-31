package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Collection;
import java.util.LinkedList;

import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FilesTab extends VerticalLayout implements FilesTabView {

	private FilesTable filesTable;
	private FilesTabSheetPresenter presenter;
	private TextField pathField;
	private CloudSession cloudSession;
	private Account account;
	
	private LinkedList<CloudFile> path;
	private CloudFile currentLocation;
	
	public FilesTab(FilesTabSheetPresenter tabSheetPresenter, Account account,
			CloudSession cloudSession) {
		this.presenter = tabSheetPresenter;
		this.account = account;
		this.cloudSession = cloudSession;
		path = new LinkedList<CloudFile>();
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
				if(event.isDoubleClick())
					changePath( (CloudFile) event.getItemId());
			}
		});


	}
	
	private void changePath(CloudFile cloudFile) {
		if(filesTable.isFirstId(cloudFile)) {
			
			getPresenter().changePath(path.pollLast(), this);
		}
		else if(cloudFile.isDirectory()){
			path.add(currentLocation);
			getPresenter().changePath(cloudFile, this);
		}
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
		pathField.setReadOnly(false);
		if(destination != null)
			pathField.setValue(destination.getFullPath());		
		else
			pathField.setValue("/");
		pathField.setReadOnly(true);
		
		if(files != null) {
			filesTable.removeAllItems();
			for (CloudFile file : files)
				filesTable.addItem(file);
			currentLocation = destination;
		}
		else
			Notification.show("Could not list files", Notification.Type.WARNING_MESSAGE);
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

	@Override
	public CloudFile getCurrentLocation() {
		return currentLocation;
	}

}
