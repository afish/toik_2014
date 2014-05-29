package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import junit.framework.TestCase;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

public class OnedriveFileManagerServiceImplTest extends TestCase {

    private OnedriveFileManagerServiceImpl fileManager;

    public void setUp() throws Exception {
        super.setUp();
        fileManager = new OnedriveFileManagerServiceImpl();
    }

    public void tearDown() throws Exception { }

    public void testDownload() throws Exception {
        ProgressTask<Boolean> ret = fileManager.download(
            "11",
            new CloudFile("ala", new Date(321321421), false, ".", "10", 231244422L),
            new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    System.out.println(b);
                }
            }
        );
        assertFalse(ret.get());
    }

    public void testRemove() throws Exception {
        ProgressTask<Boolean> ret = fileManager.remove(
            "ala",
            new CloudFile("ala", new Date(321321421), false, ".", "10", 231244422L)
        );
        assertFalse(ret.get());
    }

    public void testListFiles() throws Exception {
        ProgressTask<List<CloudFile>> listedFiles = fileManager.listFiles(
            "scalarm",
            new CloudFile("ala", new Date(321321421), false, ".", "10", 231244422L)
        );

        int cnt = 0;
        for (CloudFile cloudFile: listedFiles.get()) {
            cnt++;
        }

        assertTrue(cnt == 0);
    }

    public void testUpload() throws Exception {
        ProgressTask<CloudFile> cloudFile = fileManager.upload(
                "zzz...",
                new CloudFile("ala", new Date(321321421), false, ".", "10", 231244422L),
                "filename",
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                },
                213243234L
        );

        assertNull(cloudFile.get());
    }
}