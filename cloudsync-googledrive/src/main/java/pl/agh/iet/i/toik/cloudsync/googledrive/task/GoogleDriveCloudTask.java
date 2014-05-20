package pl.agh.iet.i.toik.cloudsync.googledrive.task;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

import java.util.concurrent.Callable;

public class GoogleDriveCloudTask<T> extends CloudTask<T> {

    public GoogleDriveCloudTask(Callable<T> callable) {
        super(callable);
    }

    @Override
    public float getProgress() {
        return 0;
    }
}
