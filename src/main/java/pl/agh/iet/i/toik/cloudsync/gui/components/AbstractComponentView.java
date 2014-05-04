package pl.agh.iet.i.toik.cloudsync.gui.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public abstract class AbstractComponentView<COMPONENT extends Component, PRESENTER extends Presenter>
		extends CustomComponent implements ComponentView<PRESENTER> {

	private PRESENTER presenter;

	protected abstract COMPONENT createContent();

	@PostConstruct
	private void init() {
		setCompositionRoot(createContent());
	}

	@Autowired
	@Override
	public void setPresenter(PRESENTER presenter) {
		this.presenter = presenter;
		this.presenter.setComponentView(this);

	}

	@Override
	public PRESENTER getPresenter() {
		return this.presenter;
	}

}
