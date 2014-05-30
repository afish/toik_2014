package pl.agh.iet.i.toik.cloudsync.gui.threads;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.agh.iet.i.toik.cloudsync.gui.components.CloseableProgressBar;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import com.vaadin.ui.UI;

public abstract class AbstractUIThread<T> extends Thread {

	private UI ui;
	private CloseableProgressBar progressBar;

	private volatile float progress = 0.0f;
	private CloudTask<T> cloudTask;

	protected abstract void finished(T result);
	
	private static Logger logger = LoggerFactory.getLogger(AbstractUIThread.class);
	
	public AbstractUIThread(UI ui, CloseableProgressBar progressBar, CloudTask<T> cloudTask) {
		this.ui = ui;
		this.progressBar = progressBar;
		this.cloudTask = cloudTask;
	}

	@Override
	public void run() {
		while (progress < 1.0f && !cloudTask.isDone()) {
			progress += cloudTask.getProgress();
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
				try {
					finished(cloudTask.get());
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}


}
