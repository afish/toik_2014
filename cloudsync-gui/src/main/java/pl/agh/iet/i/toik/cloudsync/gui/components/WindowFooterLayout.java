package pl.agh.iet.i.toik.cloudsync.gui.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

@VaadinComponent
@Scope("prototype")
public class WindowFooterLayout extends HorizontalLayout {
	
	@Autowired
	private I18N captions ;
	private Button confirmButton;
	private Button cancelButton;
	
	@PostConstruct
	private void init() {
		setSpacing(true);
		setSizeFull();
		addComponent(confirmButton = new Button());
		addComponent(cancelButton = new Button(captions
				.get("cancel.button")));
		cancelButton.setSizeUndefined();
		confirmButton.setSizeUndefined();
		setComponentAlignment(cancelButton, Alignment.BOTTOM_RIGHT);
		setComponentAlignment(confirmButton, Alignment.BOTTOM_LEFT);
	}

	public Button getConfirmButton() {
		return confirmButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}
	
	
}
