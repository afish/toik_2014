package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.presenters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pl.agh.iet.i.toik.cloudsync.gui.components.events.OpenAccountsWindowEvent;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView.FilesTabSheetPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.AbstractPresenter;

@Component
@Scope("prototype")
public class FilesTabSheetPresenterImpl extends AbstractPresenter<FilesTabSheetView> implements
		FilesTabSheetPresenter {

	@Override
	public void openAccountsWindow() {
		eventBus.publish(this, new OpenAccountsWindowEvent(getComponentView()));
	}

}
