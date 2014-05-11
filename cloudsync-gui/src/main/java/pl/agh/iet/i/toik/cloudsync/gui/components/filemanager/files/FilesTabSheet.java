package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files;

import java.util.Collection;

import org.springframework.context.annotation.Scope;
import org.vaadin.haijian.dynamictabsheet.AddButtonClickListener;
import org.vaadin.haijian.dynamictabsheet.DynamicTabSheet;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.model.AccountMock;

@VaadinComponent
@Scope("prototype")
public class FilesTabSheet extends
		AbstractComponentView<DynamicTabSheet, FilesTabSheetPresenter>
		implements FilesTabSheetView {


	private DynamicTabSheet content;

	@Override
	public void addNewTab(Collection<AccountMock> accountsMock) {
		for(AccountMock account : accountsMock)
		content.addTab(new FilesTab(), account.getAccountName());
		
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
}
