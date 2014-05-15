package pl.agh.iet.i.toik.cloudsync.onedrive.service;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.DownloadTask;

import java.io.OutputStream;

public interface OnedriveFileManagerService {

    public DownloadTask download(String sessionId, CloudFile file, OutputStream outputStream);
}
