package pl.agh.iet.i.toik.cloudsync.gui.components;

import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

public interface WindowView<PRESENTER extends Presenter> extends ComponentView<PRESENTER>{
	
	public void showWindow();
}
