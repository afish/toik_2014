package pl.agh.iet.i.toik.cloudsync.onedrive.task;

import java.util.concurrent.Callable;

public abstract class ProgressCallable<T> implements Callable<T> {
    private float progress = 0;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
