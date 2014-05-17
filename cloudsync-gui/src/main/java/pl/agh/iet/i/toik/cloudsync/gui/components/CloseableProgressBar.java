package pl.agh.iet.i.toik.cloudsync.gui.components;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.ProgressBar;

public class CloseableProgressBar extends ProgressBar {

	public CloseableProgressBar() {
		setValue(Float.valueOf(0.0f));
		setImmediate(true);
	}

	public void close() {
		((ComponentContainer)getParent()).removeComponent(this);
	}
	
}
