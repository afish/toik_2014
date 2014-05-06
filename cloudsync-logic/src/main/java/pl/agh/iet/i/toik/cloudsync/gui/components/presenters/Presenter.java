package pl.agh.iet.i.toik.cloudsync.gui.components.presenters;

import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;

public interface Presenter {

	void setComponentView(ComponentView<? extends Presenter> view);

	ComponentView<? extends Presenter> getComponentView();
}
