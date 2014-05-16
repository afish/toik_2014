package pl.agh.iet.i.toik.cloudsync.onedrive.task;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class ProgressTask<T> extends CloudTask<T> {
    private ProgressCallable<T> progressCallable;

    public ProgressTask(ProgressCallable<T> progressCallable) {
        super(progressCallable);
        this.progressCallable = progressCallable;
    }

    @Override
    public float getProgress() {
        return progressCallable.getProgress();
    }
}
