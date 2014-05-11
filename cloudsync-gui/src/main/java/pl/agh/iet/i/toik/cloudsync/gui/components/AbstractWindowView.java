package pl.agh.iet.i.toik.cloudsync.gui.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public abstract class AbstractWindowView<PRESENTER extends Presenter> extends
		Window implements WindowView<PRESENTER> {

	private PRESENTER presenter;

	@PostConstruct
	private void init() {
		setCaption(getWindowCaption());
		setClosable(true);
		setModal(true);
		setResizable(false);
		center();
		setContent(createWindowContent());
		setSizeUndefined();
	}
	
	@Autowired
	@Override
	public void setPresenter(PRESENTER presenter) {
		this.presenter = presenter;
		this.presenter.setComponentView(this);
	}

	@Override
	public PRESENTER getPresenter() {
		return presenter;
	}

	@Override
	public void showWindow() {
		UI.getCurrent().addWindow(this);
	}

	protected abstract Component createWindowContent();

	protected abstract String getWindowCaption();

}
