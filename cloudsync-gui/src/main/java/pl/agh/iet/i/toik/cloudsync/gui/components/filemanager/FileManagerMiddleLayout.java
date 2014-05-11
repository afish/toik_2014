package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.ComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.FileManagerMiddleLayout.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files.FilesTabSheet;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;
import pl.agh.iet.i.toik.cloudsync.gui.components.presenters.Presenter;

import com.vaadin.ui.HorizontalSplitPanel;

@VaadinComponent
@UIScope
public class FileManagerMiddleLayout
		extends
		AbstractComponentView<HorizontalSplitPanel, FileManagerMiddleLayoutPresenter>
		implements ComponentView<FileManagerMiddleLayoutPresenter> {

	public interface FileManagerMiddleLayoutPresenter extends Presenter {
		public void registerDefaultFilesTabSheetView(
				FilesTabSheetView tabSheetView);
	}

	@Autowired
	private FilesTabSheet leftTabSheet;

	@Autowired
	private FilesTabSheet rightTabSheet;

	@PostConstruct
	private void init() {
		getPresenter().registerDefaultFilesTabSheetView(leftTabSheet);
	}

	@Override
	protected HorizontalSplitPanel createContent() {
		HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
		splitPanel.setSplitPosition(50, Unit.PERCENTAGE);
		setSizeFull();
		leftTabSheet.setSizeFull();
		rightTabSheet.setSizeFull();
		splitPanel.setFirstComponent(leftTabSheet);
		splitPanel.setSecondComponent(rightTabSheet);
		return splitPanel;
	}
}
