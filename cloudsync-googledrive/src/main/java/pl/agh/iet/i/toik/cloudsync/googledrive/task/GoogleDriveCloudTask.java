package pl.agh.iet.i.toik.cloudsync.googledrive.task;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import java.util.concurrent.Callable;

public class GoogleDriveCloudTask<T> extends CloudTask<T> {

    private GoogleDriveCloudCallable<T> googleDriveCloudCallable;

    public GoogleDriveCloudTask(GoogleDriveCloudCallable<T> callable) {
        super(callable);
        this.googleDriveCloudCallable = callable;
    }

    @Override
    public float getProgress() {
        return googleDriveCloudCallable.getProgress();
    }
}
