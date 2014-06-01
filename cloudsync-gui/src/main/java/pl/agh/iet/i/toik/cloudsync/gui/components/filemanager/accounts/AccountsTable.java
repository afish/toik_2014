package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.accounts;

import pl.agh.iet.i.toik.cloudsync.gui.components.BeanItemTable;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

import com.vaadin.ui.Table;

public class AccountsTable extends BeanItemTable<Account> {

	public AccountsTable() {
		super(Account.class);
		addGeneratedColumn("cloud", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				if(itemId != null ){
					return ((Account)itemId).getPropertyList().get("cloud.type");
				}
				return null;
			}
		});
		setVisibleColumns("id","name","cloud");
	}

}
