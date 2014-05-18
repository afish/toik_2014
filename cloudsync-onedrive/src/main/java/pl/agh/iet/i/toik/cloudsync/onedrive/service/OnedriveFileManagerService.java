package pl.agh.iet.i.toik.cloudsync.onedrive.service;

import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressTask;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface OnedriveFileManagerService {

    public ProgressTask<Boolean> download(String sessionId, CloudFile file, OutputStream outputStream);

    public ProgressTask<Boolean> remove(String sessionId, CloudFile file);

    public ProgressTask<List<CloudFile>> listFiles(String sessionId, CloudFile directory);

    public ProgressTask<CloudFile> upload(String sessionId, CloudFile directory, String fileName, InputStream fileInputStream, Long fileSize);
}
