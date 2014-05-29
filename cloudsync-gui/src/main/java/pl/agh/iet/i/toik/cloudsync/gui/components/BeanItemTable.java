package pl.agh.iet.i.toik.cloudsync.gui.components;

import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class BeanItemTable<T> extends Table {

	
	public BeanItemTable(Class<T> beanClass){
		setSortEnabled(true);
		setImmediate(true);
		setColumnReorderingAllowed(true);
		setNullSelectionAllowed(true);
		setSelectable(true);
		setSizeFull();
		setContainerDataSource(new BeanItemContainer<T>(beanClass));
	}
	
	
	public void removeItems(Collection<T> items){
		for(T item : items)
			removeItem(item);
	}
	
}
