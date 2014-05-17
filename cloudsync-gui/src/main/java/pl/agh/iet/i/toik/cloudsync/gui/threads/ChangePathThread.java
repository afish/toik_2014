package pl.agh.iet.i.toik.cloudsync.gui.threads;

import java.util.ArrayList;
import java.util.List;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.gui.components.filemanager.views.FilesTabView;
import pl.agh.iet.i.toik.cloudsync.gui.model.FileMock;

import com.vaadin.ui.UI;

public class ChangePathThread extends AbstractUIThread<FileMock> {

	private FilesTabView tab;

	public ChangePathThread(UI ui, CloseableProgressBar progressBar, FileMock mockResult, FilesTabView tab) {
		super(ui, progressBar, mockResult);
		this.tab = tab;
	}

	@Override
	protected void finished(FileMock result) {
		List<FileMock> files = new ArrayList<FileMock>();
		files.add(result);
		tab.refresh("newPath", files );
	}

}
