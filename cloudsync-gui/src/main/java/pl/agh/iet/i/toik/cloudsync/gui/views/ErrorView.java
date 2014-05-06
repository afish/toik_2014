package pl.agh.iet.i.toik.cloudsync.gui.views;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;

@VaadinComponent
@UIScope
public class ErrorView extends AbstractComponent implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		UI.getCurrent().getNavigator().navigateTo("");
	}

}
