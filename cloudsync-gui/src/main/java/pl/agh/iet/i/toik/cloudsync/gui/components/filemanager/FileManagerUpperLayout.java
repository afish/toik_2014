package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import java.util.Iterator;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@VaadinComponent
@UIScope
public class FileManagerUpperLayout extends
		AbstractComponentView<VerticalLayout, FileManagerUpperLayoutPresenter> {

	public interface FileManagerUpperLayoutPresenter extends Presenter {
			
			void deleteAction();
	}

	private static final String ACCOUNTS_BUTTON_CAPTION = "Accounts";
	private static final String COPY_BUTTON_CAPTION = "Copy";
	private static final String DELETE_BUTTON_CAPTION = "Delete";
	private static final String LABEL_CAPTION = "Cloud synchronization";

	private Button accountsButton;
	private Button helpButton;
	private Button copyButton;
	private Button deleteButton;
	private Component captionLabel;

	@Override
	protected VerticalLayout createContent() {
		return initContent();
	}

	private VerticalLayout initContent() {
		VerticalLayout contentLayout = new VerticalLayout();
		setLayoutCommonProperties(contentLayout);
		contentLayout.addComponent(createUpperLayout());
		Layout footerLayot;
		contentLayout.addComponent(footerLayot = createFooterLayout());
		contentLayout.setComponentAlignment(footerLayot, Alignment.BOTTOM_CENTER);
		return contentLayout;
	}

	private void setLayoutCommonProperties(AbstractOrderedLayout contentLayout) {
		contentLayout.setSpacing(true);
		contentLayout.setSizeFull();
	}

	private Layout createFooterLayout() {
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setSpacing(true);
		
		footerLayout.addComponent(copyButton = new Button(COPY_BUTTON_CAPTION));
		footerLayout.addComponent(deleteButton = new Button(DELETE_BUTTON_CAPTION));
		
		setComponentsSizeUndefined(footerLayout.iterator());
		
		footerLayout.setComponentAlignment(copyButton, Alignment.BOTTOM_CENTER);
		footerLayout.setComponentAlignment(deleteButton, Alignment.BOTTOM_CENTER);
		
		deleteButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().deleteAction();
				
			}
		});
		
		return footerLayout;
	}

	private Layout createUpperLayout() {
		HorizontalLayout upperLayout = new HorizontalLayout();
		setLayoutCommonProperties(upperLayout);
		upperLayout.addComponent(accountsButton = new Button(ACCOUNTS_BUTTON_CAPTION));
		upperLayout.addComponent(captionLabel = new Label(LABEL_CAPTION));
		upperLayout.addComponent(helpButton = new Button());
		
		setComponentsSizeUndefined(upperLayout.iterator());

		upperLayout.setComponentAlignment(accountsButton, Alignment.BOTTOM_LEFT);
		upperLayout.setComponentAlignment(captionLabel, Alignment.TOP_CENTER);
		upperLayout.setComponentAlignment(helpButton, Alignment.BOTTOM_RIGHT);


		return upperLayout;
	}

	private void setComponentsSizeUndefined(Iterator<Component> iterator) {
		while (iterator.hasNext())
			iterator.next().setSizeUndefined();

	}



}
