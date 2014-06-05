package pl.agh.iet.i.toik.cloudsync.onedrive;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveFileManagerService;
import pl.agh.iet.i.toik.cloudsync.onedrive.task.ProgressTask;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OnedriveCloudTest {

    private OnedriveCloud cloud;

    private OnedriveAccountService mockOnedriveAccountService;
    private OnedriveFileManagerService mockOnedriveFileManagerService;

    private String sessionId = "12345";
    private ProgressTask result;
    private Session session;
    private CloudFile folder;

    @Before
    public void setUp() throws Exception {
        mockOnedriveAccountService = mock(OnedriveAccountService.class);
        mockOnedriveFileManagerService = mock(OnedriveFileManagerService.class);

        cloud = new OnedriveCloud();
        ReflectionTestUtils.setField(cloud, "onedriveAccountService", mockOnedriveAccountService);
        ReflectionTestUtils.setField(cloud, "onedriveFileManagerService", mockOnedriveFileManagerService);

        result = mock(ProgressTask.class);

        session = mock(Session.class);
        folder = mock(CloudFile.class);
        when(mockOnedriveAccountService.getSession(sessionId)).thenReturn(session);
        when(session.getRootFolder()).thenReturn(folder);
    }

    @Test
    public void testLogin() throws Exception {
        String loginResult = "123456789";
        Account account = new Account("qwerty", "asdfgh", new HashMap<String, Object>());

        when(mockOnedriveAccountService.login(account)).thenReturn(loginResult);

        assertEquals(loginResult, cloud.login(account));
        verify(mockOnedriveAccountService, times(1)).login(account);
    }

    @Test
    public void testLogout() throws Exception {

        cloud.logout(sessionId);
        verify(mockOnedriveAccountService, times(1)).logout(sessionId);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testListAllFiles() throws Exception {
        when(mockOnedriveFileManagerService.listFiles(sessionId, folder)).thenReturn(result);

        assertEquals(result, cloud.listAllFiles(sessionId, null));
        verify(session, times(1)).getRootFolder();
        verify(mockOnedriveAccountService, times(1)).getSession(sessionId);

        assertEquals(result, cloud.listAllFiles(sessionId, folder));
        verify(mockOnedriveFileManagerService, times(2)).listFiles(sessionId, folder);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDownload() throws Exception {

        OutputStream outputStream = mock(OutputStream.class);

        when(mockOnedriveFileManagerService.download(sessionId, folder, outputStream)).thenReturn(result);

        assertEquals(result, cloud.download(sessionId, folder, outputStream));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemove() throws Exception {
        when(mockOnedriveFileManagerService.remove(sessionId, folder)).thenReturn(result);

        assertEquals(result, cloud.remove(sessionId, folder));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUpload() throws Exception {
        String fileName = "filename";
        InputStream stream = mock(InputStream.class);
        Long size = 10L;

        when(mockOnedriveFileManagerService.upload(sessionId, folder, fileName, stream, size)).thenReturn(result);

        assertEquals(result, cloud.upload(sessionId, null, fileName, stream, size));
        verify(session, times(1)).getRootFolder();
        verify(mockOnedriveAccountService, times(1)).getSession(sessionId);

        assertEquals(result, cloud.upload(sessionId, folder, fileName, stream, size));

        verify(mockOnedriveFileManagerService, times(2)).upload(sessionId, folder, fileName, stream, size);
    }
}