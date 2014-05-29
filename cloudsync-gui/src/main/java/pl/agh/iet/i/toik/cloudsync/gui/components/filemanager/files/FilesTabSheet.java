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

import com.vaadin.ui.Component;

@VaadinComponent
@Scope("prototype")
public class FilesTabSheet extends
		AbstractComponentView<DynamicTabSheet, FilesTabSheetPresenter>
		implements FilesTabSheetView {


	private DynamicTabSheet content;

	@Override
	public void addNewTab(Account account) {
		content.addTab(new FilesTab(getPresenter()), account.getName());
		
	}

	@Override
	protected DynamicTabSheet createContent() {
		content = new DynamicTabSheet();
		content.setAddButtonClickListener(new AddButtonClickListener() {
			
			@Override
			public void onAddNewTab() {
				getPresenter().openAccountsWindow();
				
			}
		});
		content.setSizeFull();
		return content;
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
}
