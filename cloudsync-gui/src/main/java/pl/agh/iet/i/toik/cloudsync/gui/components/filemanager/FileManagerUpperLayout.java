package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.i18n.I18N;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerUpperLayout.FileManagerUpperLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.server.ThemeResource;
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
import com.vaadin.ui.themes.Reindeer;

@VaadinComponent
@UIScope
public class FileManagerUpperLayout extends
		AbstractComponentView<VerticalLayout, FileManagerUpperLayoutPresenter> {

	public interface FileManagerUpperLayoutPresenter extends Presenter {
						
			public void openAccountsWindow();
			public void setDefaultFilesTabSheetView(FilesTabSheetView tabSheetView);
			public void deleteAction();
			public void setMiddleLayoutPresenter(
					FileManagerMiddleLayoutPresenter fileManagerMiddleLayoutPresenterImpl);
			public void copyAction();
			public void moveAction();
	}
	
	@Autowired
	private I18N captions;

	private Button accountsButton;
	private Button helpButton;
	private Button copyButton;
	private Button deleteButton;
	private Button moveButton;
	private Component captionLabel;
	
	@PostConstruct
	private void intit(){
		accountsButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().openAccountsWindow();
				
			}
		});
		
		deleteButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().deleteAction();
				
			}
		});
		
		copyButton.addClickListener( new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().copyAction();
			}
		});
		
		moveButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getPresenter().moveAction();
			}
		});
	}
	@Override
	protected VerticalLayout createContent() {
		return initContent();
	}

	private VerticalLayout initContent() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setSpacing(true);
		contentLayout.addComponent(createUpperLayout());
		Layout footerLayot;
		contentLayout.addComponent(footerLayot = createFooterLayout());
		contentLayout.setComponentAlignment(footerLayot, Alignment.BOTTOM_CENTER);
		return  contentLayout;
	}

	private void setLayoutCommonProperties(AbstractOrderedLayout contentLayout) {
		contentLayout.setSpacing(true);
		contentLayout.setSizeFull();
	}

	private Layout createFooterLayout() {
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setSpacing(true);
		
		footerLayout.addComponent(copyButton = new Button(captions.get("copy.button")));
		footerLayout.addComponent(moveButton = new Button(captions.get("move.button")));
		footerLayout.addComponent(deleteButton = new Button(captions.get("delete.button")));
		
		setFooterLayoutComponentsProperties(footerLayout);
		
		return footerLayout;
	}

	private void setFooterLayoutComponentsProperties(
			HorizontalLayout footerLayout) {
		Iterator<Component> componentIterator = footerLayout.iterator();
		setComponentsSizeUndefined(componentIterator);
		
		while(componentIterator.hasNext())
			footerLayout.setComponentAlignment(componentIterator.next(), Alignment.BOTTOM_CENTER);
		
	}
	private Layout createUpperLayout() {
		HorizontalLayout upperLayout = new HorizontalLayout();
		setLayoutCommonProperties(upperLayout);
		upperLayout.addComponent(accountsButton = new Button(captions.get("accounts.button")));
		upperLayout.addComponent(captionLabel = new Label(captions.get("page.title")));
		upperLayout.addComponent(helpButton = new Button());
		
		helpButton.setIcon(new ThemeResource("help.ico"));
		helpButton.setStyleName(Reindeer.BUTTON_LINK);
		
		captionLabel.addStyleName(Reindeer.LABEL_H1);
		setComponentsSizeUndefined(upperLayout.iterator());

		upperLayout.setComponentAlignment(accountsButton, Alignment.BOTTOM_LEFT);
		upperLayout.setComponentAlignment(captionLabel, Alignment.TOP_CENTER);
		upperLayout.setComponentAlignment(helpButton, Alignment.TOP_RIGHT);


		return upperLayout;
	}

	private void setComponentsSizeUndefined(Iterator<Component> iterator) {
		while (iterator.hasNext())
			iterator.next().setSizeUndefined();

	}



}
