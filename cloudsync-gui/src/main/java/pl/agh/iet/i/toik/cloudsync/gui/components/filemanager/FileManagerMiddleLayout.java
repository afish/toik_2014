package pl.agh.iet.i.toik.cloudsync.gui.components.filemanager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import pl.agh.iet.i.toik.cloudsync.gui.components.AbstractComponentView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.files.FilesTabSheet;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FileManagerMiddleLayoutView.FileManagerMiddleLayoutPresenter;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabSheetView;

import com.vaadin.ui.HorizontalSplitPanel;

@VaadinComponent
@UIScope
public class FileManagerMiddleLayout
		extends
		AbstractComponentView<HorizontalSplitPanel, FileManagerMiddleLayoutPresenter>
		implements FileManagerMiddleLayoutView {
	
	@Autowired
	private FilesTabSheet leftTabSheet;

	@Autowired
	private FilesTabSheet rightTabSheet;

	private FilesTabSheetView currentTabSheet;

	@PostConstruct
	private void init() {
		getPresenter().registerDefaultFilesTabSheetView(leftTabSheet);
		this.currentTabSheet = leftTabSheet;
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

	@Override
	public void setCurrentTabSheet(FilesTabSheetView tabSheetView) {
		this.currentTabSheet = tabSheetView;
	}

	@Override
	public FilesTabSheetView getCurrentTabSheet() {
		return currentTabSheet;
	}

}
