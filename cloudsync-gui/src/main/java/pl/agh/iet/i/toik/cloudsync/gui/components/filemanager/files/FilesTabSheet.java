package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.vaadin.haijian.dynamictabsheet.AddButtonClickListener;
import org.vaadin.haijian.dynamictabsheet.DynamicTabSheet;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudSession;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;

@VaadinComponent
@Scope("prototype")
public class FilesTabSheet extends
		AbstractComponentView<DynamicTabSheet, FilesTabSheetPresenter>
		implements FilesTabSheetView {


	private DynamicTabSheet content;


	@Override
	protected DynamicTabSheet createContent() {
		content = new DynamicTabSheet();
		setListeners();
		content.setSizeFull();
		content.setImmediate(true);
		return content;
	}

	private void setListeners() {
		content.setAddButtonClickListener(new AddButtonClickListener() {
			
			@Override
			public void onAddNewTab() {
				getPresenter().openAccountsWindow();
				
			}
		});
		
		content.setCloseHandler(new CloseHandler() {
			
			@Override
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				getPresenter().logout((FilesTabView)tabContent);
				tabsheet.removeComponent(tabContent);
			}
		});
	}

	@Override
	public void unselect() {
		Iterator<Component> tabIterator = content.iterator();
		FilesTabView tab ;
		while(tabIterator.hasNext()) {
			tab = (FilesTabView) tabIterator.next();
			tab.unselect();
		}
	}

	@Override
	public void addNewTab(CloudSession cloudSession, Account account) {
		content.addTab(new FilesTab(getPresenter(), account, cloudSession), account.getName());
		
	}

	@Override
	public FilesTabView getCurrentTab() {
		return (FilesTabView) content.getSelectedTab();
	}
}
