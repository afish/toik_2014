package pl.agh.iet.i.toik.cloudsync.gui.components;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;

public class CloseableProgressBar extends ProgressBar {

	public CloseableProgressBar() {
		setValue(Float.valueOf(0.0f));
		setImmediate(true);
	}

	public void closeWithSuccess() {
		closeMe("SUCCESS");
	
	}
	
	public void closeWithFailure() {
		closeMe("FAILURE");
	}
	private void closeMe(String message) {
		((ComponentContainer)getParent()).removeComponent(this);
		Notification.show(getCaption()+" completed with: ",message, Notification.Type.TRAY_NOTIFICATION);
	}
	
}
