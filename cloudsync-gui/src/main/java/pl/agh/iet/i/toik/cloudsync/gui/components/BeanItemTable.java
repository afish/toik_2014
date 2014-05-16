package pl.agh.iet.i.toik.cloudsync.gui.components;

import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class BeanItemTable<T> extends Table {

	private BeanItemContainer<T> beanItemContainer;
	
	public BeanItemTable(Class<T> beanClass){
		setSortEnabled(true);
		setImmediate(true);
		setColumnReorderingAllowed(true);
		setNullSelectionAllowed(true);
		setSelectable(true);
		setMultiSelect(true);
		setSizeFull();
		setContainerDataSource(beanItemContainer = new BeanItemContainer<T>(beanClass));
	}
	
	public void addItems(Collection<T> items){
		beanItemContainer.addAll(items);
	}
	
	public void removeItems(Collection<T> items){
		for(T item : items)
			removeItem(item);
	}
	
}
