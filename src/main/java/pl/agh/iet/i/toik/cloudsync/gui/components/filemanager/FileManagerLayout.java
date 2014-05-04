package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerLayout.FileManagerLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@VaadinComponent
@UIScope
public class FileManagerLayout extends
		AbstractComponentView<VerticalLayout, FileManagerLayoutPresenter> {

	public interface FileManagerLayoutPresenter extends Presenter {
		public String getHelloMessage();
	}

	@Override
	protected VerticalLayout createContent() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.addComponent(new Label(getPresenter().getHelloMessage()));
		return contentLayout;
	}
}
