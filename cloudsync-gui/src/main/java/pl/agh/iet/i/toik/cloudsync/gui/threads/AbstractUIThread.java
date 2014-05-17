package pl.agh.iet.i.toik.cloudsync.gui.threads;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;

import com.vaadin.ui.UI;

public abstract class AbstractUIThread<T> extends Thread {

	private UI ui;
	private CloseableProgressBar progressBar;

	private volatile float progress = 0.0f;
	private T result;

	protected abstract void finished(T result);

	public AbstractUIThread(UI ui, CloseableProgressBar progressBar, T mockResult) {
		this.ui = ui;
		this.progressBar = progressBar;
		this.result = mockResult;
	}

	@Override
	public void run() {
		while (progress < 1.0f) {
			progress += 0.1f;
			updateProgessBar(progress);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		finish();
		removeProgressBar();
	}

	private void removeProgressBar() {
		ui.access(new Runnable() {

			@Override
			public void run() {			
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progressBar.close();
			}
		});

	}

	private void updateProgessBar(final float currentProgress) {
		ui.access(new Runnable() {

			@Override
			public void run() {
				progressBar.setValue(new Float(currentProgress));
			}
		});

	}
	
	private void finish() {
		ui.access(new Runnable() {

			@Override
			public void run() {
				finished(result);
			}
		});
	}


}
