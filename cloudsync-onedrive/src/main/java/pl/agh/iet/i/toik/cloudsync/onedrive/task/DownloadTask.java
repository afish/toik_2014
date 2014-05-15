package pl.agh.iet.i.toik.cloudsync.onedrive.task;

import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;

public class DownloadTask extends CloudTask<Boolean> {
    DownloadCallable downloadCallable;

    public DownloadTask(DownloadCallable downloadCallable) {
        super(downloadCallable);
        this.downloadCallable = downloadCallable;
    }

    @Override
    public float getProgress() {
        return downloadCallable.getProgress();
    }
}
