package pl.agh.iet.i.toik.cloudsync.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.SpringViewProvider;

import pl.agh.iet.i.toik.cloudsync.gui.views.ErrorView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@VaadinUI
@Theme("cloud")
@Title("Cloud synchronization")
@Widgetset("pl.agh.iet.i.toik.cloudsync.gui.AppWidgetSet")
public class MainUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private ErrorView errorView;

	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout content = new VerticalLayout();
		content.setSizeFull();
		setContent(content);
		
		Navigator navigator = new Navigator(this, content);
		navigator.addProvider(viewProvider);
		navigator.setErrorView(errorView);
		setNavigator(navigator);
		
	}
	
	
}
