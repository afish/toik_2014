package pl.agh.iet.i.toik.cloudsync.googledrive.task;

import java.util.concurrent.Callable;

/**
 * Created by Anna on 2014-05-25.
 */
public abstract class GoogleDriveCloudCallable<T> implements Callable<T> {

    private float progress = 0;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
