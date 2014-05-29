package pl.agh.iet.i.toik.cloudsync.onedrive.task;

import junit.framework.TestCase;

public class ProgressTaskTest extends TestCase {

    private ProgressTask<String> progressTask;

    public void setUp() throws Exception {
        super.setUp();
        progressTask = new ProgressTask<>(new ProgressCallable<String>() {
            @Override
            public String call() throws Exception {
                return "e4c5Nf3Nc6";
            }
        });
    }

    public void tearDown() throws Exception { }

    public void testGetProgress() throws Exception {
        assertTrue(progressTask.getProgress() >= 0);
    }
}