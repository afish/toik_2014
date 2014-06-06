package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.logic.CloudTask;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveFileManagerService;
import pl.agh.iet.i.toik.cloudsync.onedrive.util.JSONResolver;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnedriveFileManagerServiceImplTest {

    private OnedriveFileManagerService service;

    private JSONResolver mockJSONREResolver;
    private WebResource mockWebResource;
    private ClientResponse mockClientResponse;
    private OnedriveAccountService mockOnedriveAccountService;

    private String sessionId = "session_id";
    private String jsonResponse = "json_response";
    private String accessToken = "access_token";

    @Before
    public void setUp() throws Exception {

        service = new OnedriveFileManagerServiceImpl();

        mockJSONREResolver = mock(JSONResolver.class);
        Client mockClient = mock(Client.class);
        mockOnedriveAccountService = mock(OnedriveAccountService.class);
        mockWebResource = mock(WebResource.class);
        mockClientResponse = mock(ClientResponse.class);

        ReflectionTestUtils.setField(service, "jsonResolver", mockJSONREResolver);
        ReflectionTestUtils.setField(service, "client", mockClient);
        ReflectionTestUtils.setField(service, "onedriveAccountService", mockOnedriveAccountService);

        when(mockClient.resource(anyString())).thenReturn(mockWebResource);
        when(mockWebResource.queryParam(anyString(), anyString())).thenReturn(mockWebResource);
        when(mockOnedriveAccountService.getAccessToken(sessionId)).thenReturn(accessToken);

        //getting response
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        when(mockWebResource.delete(ClientResponse.class)).thenReturn(mockClientResponse);
        when(mockWebResource.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
        when(mockClientResponse.getEntity(String.class)).thenReturn(jsonResponse);
        when(mockClientResponse.getStatus()).thenReturn(200);
    }

    @Test
    public void shouldRemoveFileProperly() throws Exception {
        CloudFile file = mock(CloudFile.class);

        when(mockClientResponse.getStatus()).thenReturn(204);

        Boolean result = (Boolean) executeTaskAndGetResult(service.remove(sessionId, file));
        assertTrue(result);
    }

    @Test
    public void shouldNotRemoveFile() throws Exception {
        CloudFile file = mock(CloudFile.class);

        Boolean result = (Boolean) executeTaskAndGetResult(service.remove(sessionId, file));
        assertFalse(result);

        when(mockOnedriveAccountService.getAccessToken(sessionId)).thenReturn(null);
        result = (Boolean) executeTaskAndGetResult(service.remove(sessionId, file));
        assertFalse(result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldProperlyListFiles() throws Exception {
        ArrayList<CloudFile> filesList = new ArrayList<>();
        filesList.add(new CloudFile("", new java.util.Date(), true, "", "", 10L));
        filesList.add(new CloudFile("", new java.util.Date(), true, "", "", 10L));

        CloudFile file = mock(CloudFile.class);

        when(file.isDirectory()).thenReturn(true);
        when(mockJSONREResolver.resolveFileListDetails(jsonResponse, file)).thenReturn(filesList);

        List<CloudFile> result = (List<CloudFile>) executeTaskAndGetResult(service.listFiles(sessionId, file));

        assertEquals(filesList, result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnEmptyFileList() throws Exception {
        CloudFile file = mock(CloudFile.class);

        when(file.isDirectory()).thenReturn(false);

        List<CloudFile> result = (List<CloudFile>) executeTaskAndGetResult(service.listFiles(sessionId, file));
        assertTrue(result.isEmpty());

        when(mockClientResponse.getStatus()).thenReturn(666);
        when(file.isDirectory()).thenReturn(true);

        result = (List<CloudFile>) executeTaskAndGetResult(service.listFiles(sessionId, file));
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldProperlyDownloadFile() throws Exception {
        CloudFile file = mock(CloudFile.class);

        InputStream inputStream = mock(InputStream.class);
        OutputStream outputStream = mock(OutputStream.class);

        when(file.isDirectory()).thenReturn(false);
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenReturn(-1);
        when(mockClientResponse.getEntityInputStream()).thenReturn(inputStream);

        Boolean result = (Boolean) executeTaskAndGetResult(service.download(sessionId, file, outputStream));
        assertTrue(result);
    }

    @Test
    public void shouldFailWhileDownloadingFile() throws Exception {
        CloudFile file = mock(CloudFile.class);

        InputStream inputStream = mock(InputStream.class);
        OutputStream outputStream = mock(OutputStream.class);

        when(file.isDirectory()).thenReturn(false);
        when(inputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException());
        when(mockClientResponse.getEntityInputStream()).thenReturn(inputStream);

        Boolean result = (Boolean) executeTaskAndGetResult(service.download(sessionId, file, outputStream));
        assertFalse(result);

        when(mockClientResponse.getStatus()).thenReturn(201);

        result = (Boolean) executeTaskAndGetResult(service.download(sessionId, file, outputStream));
        assertFalse(result);

        when(file.isDirectory()).thenReturn(true);

        result = (Boolean) executeTaskAndGetResult(service.download(sessionId, file, outputStream));
        assertFalse(result);
    }

    private Object executeTaskAndGetResult(final CloudTask<?> task) throws ExecutionException, InterruptedException {
        new Thread() {
            @Override
            public void run() {
                task.run();
            }
        }.start();

        return task.get();
    }
}