package pl.agh.iet.i.toik.cloudsync.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.SpringViewProvider;

import pl.agh.iet.i.toik.cloudsync.gui.views.ErrorView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@VaadinUI
@Theme("reindeer")
@Title("Cloud synchronization")
public class MainUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private ErrorView errorView;

	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(errorView);
		setNavigator(navigator);
	}
	
	
}
