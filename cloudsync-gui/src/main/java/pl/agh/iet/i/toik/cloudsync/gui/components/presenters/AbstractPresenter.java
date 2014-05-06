package pl.agh.iet.i.toik.cloudsync.gui.components.presenters;

import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;

@Component
public abstract class AbstractPresenter<VIEW extends ComponentView<? extends Presenter>>
		implements Presenter {

	private VIEW view;

	@Override
	public void setComponentView(ComponentView<? extends Presenter> view) {
		this.view = (VIEW) view;

	}

	@Override
	public VIEW getComponentView() {
		return view;
	}

}
