package pl.agh.iet.i.toik.cloudsync.gui.components;

import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.Component;

public interface ComponentView<PRESENTER extends Presenter> extends Component {
	
	void setPresenter(PRESENTER presenter);
	
	PRESENTER getPresenter();
}
