package pl.agh.iet.i.toik.cloudsync.onedrive.service;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressTask;

import java.io.OutputStream;

public interface OnedriveFileManagerService {

    public ProgressTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream);
    public ProgressTask<Boolean> remove(String sessionId, CloudFile file);
}
