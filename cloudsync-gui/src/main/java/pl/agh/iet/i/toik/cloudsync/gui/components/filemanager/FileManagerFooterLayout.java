package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import javax.annotation.PostConstruct;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerFooterLayoutView.FileManagerFooterLayoutPresenter;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
@UIScope
public class FileManagerFooterLayout extends
		AbstractComponentView<VerticalLayout, FileManagerFooterLayoutPresenter>
		implements FileManagerFooterLayoutView {

	private VerticalLayout content;

	@PostConstruct
	private void init() {
		
	}

	@Override
	public CloseableProgressBar addProgressBar(String caption) {
		CloseableProgressBar progressBar = new CloseableProgressBar();
		progressBar.setCaption(caption);
		progressBar.setSizeFull();
		content.addComponent(progressBar);
		return progressBar;
	}

	@Override
	protected VerticalLayout createContent() {
		content = new VerticalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		return content;
	}
	
}
