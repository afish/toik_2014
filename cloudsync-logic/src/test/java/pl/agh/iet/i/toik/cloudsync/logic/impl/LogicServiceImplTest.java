package pl.agh.iet.i.toik.cloudsync.logic.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.agh.iet.i.toik.cloudsync.logic.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.*;

public class LogicServiceImplTest {
    private LogicServiceImpl service;

    @Before
    public void init(){
        service = new LogicServiceImpl();
    }

    @Test
    public void testLogin(){
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        Account account = new Account(null, null, null);

        service.login(information, account);

        verify(cloud, times(1)).login(account);
    }

    @Test
    public void testLogout(){
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        CloudSession session = new CloudSession(information, "1");

        service.logout(session);

        verify(cloud, times(1)).logout("1");
    }

    @Test
    public void testDelete() throws ExecutionException, InterruptedException, TimeoutException {
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        CloudSession session = new CloudSession(information, "1");
        CloudFile file = new CloudFile(null, null, false, null, null, 0l);
        CloudTask<Boolean> task = new FakeCloudTask<>(new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        when(cloud.remove("1", file)).thenReturn(task);

        service.delete(session, file);
        Assert.assertTrue(task.get(1, TimeUnit.SECONDS));
    }

    @Test
    public void testListFiles() throws InterruptedException, ExecutionException, TimeoutException {
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        CloudSession session = new CloudSession(information, "1");
        CloudFile file = new CloudFile(null, null, false, null, null, 0l);
        final List<CloudFile> list = new ArrayList<>();
        CloudTask<List<CloudFile>> task = new FakeCloudTask<>(new Callable<List<CloudFile>>(){
            @Override
            public List<CloudFile> call() throws Exception {
                return list;
            }
        });

        when(cloud.listAllFiles("1", file)).thenReturn(task);

        service.listFiles(session, file);
        Assert.assertEquals(task.get(1, TimeUnit.SECONDS), list);
    }

    @Test
    public void testCopy() throws InterruptedException, ExecutionException, TimeoutException {
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        CloudSession session = new CloudSession(information, "1");
        final CloudFile file = new CloudFile(null, null, false, null, null, 0l);
        CloudTask<Boolean> downloadTask = new FakeCloudTask<>(new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });
        CloudTask<CloudFile> uploadTask = new FakeCloudTask<>(new Callable<CloudFile>(){
            @Override
            public CloudFile call() throws Exception {
                return file;
            }
        });

        when(cloud.download(eq("1"), eq(file), any(OutputStream.class))).thenReturn(downloadTask);
        when(cloud.upload(eq("1"), eq(file), (String)eq(null), any(InputStream.class), eq(0l))).thenReturn(uploadTask);

        service.copy(session, file, session, file, null);

        Assert.assertTrue(downloadTask.get(1, TimeUnit.SECONDS));
        Assert.assertEquals(uploadTask.get(1, TimeUnit.SECONDS), file);
    }

    @Test
    public void testMove() throws InterruptedException, ExecutionException, TimeoutException {
        Cloud cloud = mock(Cloud.class);
        CloudInformation information = new CloudInformation(null, null, cloud);
        CloudSession session = new CloudSession(information, "1");
        final CloudFile file = new CloudFile(null, null, false, null, null, 0l);
        CloudTask<Boolean> downloadTask = new FakeCloudTask<>(new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });
        CloudTask<CloudFile> uploadTask = new FakeCloudTask<>(new Callable<CloudFile>(){
            @Override
            public CloudFile call() throws Exception {
                return file;
            }
        });
        CloudTask<Boolean> deleteTask = new FakeCloudTask<>(new Callable<Boolean>(){
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        });

        when(cloud.download(eq("1"), eq(file), any(OutputStream.class))).thenReturn(downloadTask);
        when(cloud.upload(eq("1"), eq(file), (String) eq(null), any(InputStream.class), eq(0l))).thenReturn(uploadTask);
        when(cloud.remove(eq("1"), eq(file))).thenReturn(deleteTask);

        service.move(session, file, session, file, null);

        Assert.assertTrue(downloadTask.get(1, TimeUnit.SECONDS));
        Assert.assertEquals(uploadTask.get(1, TimeUnit.SECONDS), file);
        Assert.assertTrue(deleteTask.get(1, TimeUnit.SECONDS));
    }

    class FakeCloudTask<T> extends CloudTask<T>{

        public FakeCloudTask(Callable<T> callable) {
            super(callable);
        }

        @Override
        public float getProgress() {
            return 0;
        }
    }
}
