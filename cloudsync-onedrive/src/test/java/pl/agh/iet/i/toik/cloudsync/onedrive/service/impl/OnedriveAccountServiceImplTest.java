package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.Session;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnedriveAccountServiceImplTest {

    private OnedriveAccountService service;
    private ClientResponse mockClientResponse;


    @Before
    public void setUp() throws Exception {
        service = new OnedriveAccountServiceImpl();

        Client mockClient = mock(Client.class);

        WebResource mockWebResource = mock(WebResource.class);
        mockClientResponse = mock(ClientResponse.class);

        ReflectionTestUtils.setField(service, "client", mockClient);

        when(mockClient.resource(anyString())).thenReturn(mockWebResource);
        when(mockWebResource.queryParam(anyString(), anyString())).thenReturn(mockWebResource);

        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        when(mockWebResource.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
        when(mockClientResponse.getStatus()).thenReturn(200);
    }

    @Test
    public void shouldFailGettingTokensWhileLogin() throws Exception {
        Account account = createSimpleAccount(false, true);

        when(mockClientResponse.getStatus()).thenReturn(66);

        String result = service.login(account);

        assertNull(result);
    }

    @Test
    public void shouldFailRefreshingTokensWhileLogin() throws Exception {
        Account account = createSimpleAccount(true, true);
        String wrongServerResponse = loadJSONResponseFromFile("account_service/wrongLoginResponse.json");

        when(mockClientResponse.getEntity(String.class)).thenReturn(wrongServerResponse);

        String result = service.login(account);

        assertNull(result);

    }

    @Test
    public void shouldPassLoginWithDefinedProperties() throws Exception {
        Account account = createSimpleAccount(false, true);
        String properServerResponse = loadJSONResponseFromFile("account_service/properLoginResponse.json");

        when(mockClientResponse.getEntity(String.class)).thenReturn(properServerResponse);

        String result = service.login(account);
        Map<String, Session> sessionMap = getSessionMap(service);

        assertNotNull(result);
        assertTrue(sessionMap.containsKey(result));

        Session session = sessionMap.get(result);

        assertEquals("000000004811C246", session.getClientId());
        assertEquals("2KPk-kGUZJVmQXGdyLwZ8PpGHWmKoeSX", session.getClientSecret());
        assertEquals("http:%2F%2Fonedrive.toik.pl%2F", session.getRedirectURI());
        assertEquals("folder.fa6a470fcef1fb9a", session.getRootFolder().getId());
        assertEquals("was toooooo looooong", session.getAccessToken());
        assertEquals("json refresh token", session.getRefreshToken());
    }

    @Test
    public void shouldPassLoginWithDefaultProperties() throws Exception {
        Account account = createSimpleAccount(true, false);
        String properServerResponse = loadJSONResponseFromFile("account_service/properLoginResponse.json");

        when(mockClientResponse.getEntity(String.class)).thenReturn(properServerResponse);

        String result = service.login(account);
        Map<String, Session> sessionMap = getSessionMap(service);

        assertNotNull(result);
        assertTrue(sessionMap.containsKey(result));

        Session session = sessionMap.get(result);

        assertEquals("000000004811C246", session.getClientId());
        assertEquals("2KPk-kGUZJVmQXGdyLwZ8PpGHWmKoeSX", session.getClientSecret());
        assertEquals("https:%2F%2Flogin.live.com%2Foauth20_desktop.srf", session.getRedirectURI());
        assertEquals("me/skydrive", session.getRootFolder().getId());
        assertEquals("was toooooo looooong", session.getAccessToken());
        assertEquals("refresh properties token", session.getRefreshToken());
    }

    @Test
    public void testLogout() throws Exception {
        Map<String, Session> sessionMap = getSessionMap(service);
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);

        String session1Id = "1234";
        String session2Id = "0909";

        sessionMap.put(session1Id, session1);
        sessionMap.put(session2Id, session2);

        service.logout(session1Id);
        assertFalse(sessionMap.containsKey(session1Id));

        service.logout(session2Id);
        assertFalse(sessionMap.containsKey(session2Id));

        service.logout(session2Id);
    }

    @Test
    public void shouldReturnAccessToken() throws Exception {
        Map<String, Session> sessionMap = getSessionMap(service);

        String accessToken = "access_token";
        String sessionId = "asdf1234";
        Session session = createSimpleSession();
        sessionMap.put(sessionId, session);

        DateTime expirationTime = new DateTime().plusMinutes(100);
        session.setTokenExpirationDate(expirationTime);
        session.setAccessToken(accessToken);

        assertEquals(accessToken, service.getAccessToken(sessionId));
    }

    @Test
    public void shouldRefreshAndReturnAccessToken() throws Exception {
        Map<String, Session> sessionMap = getSessionMap(service);

        String accessToken = "access_token";
        String sessionId = "asdf1234";
        Session session = createSimpleSession();
        sessionMap.put(sessionId, session);

        DateTime expirationTime = new DateTime().minusMinutes(100);
        session.setTokenExpirationDate(expirationTime);
        session.setAccessToken(accessToken);

        String properServerResponse = loadJSONResponseFromFile("account_service/properLoginResponse.json");

        when(mockClientResponse.getEntity(String.class)).thenReturn(properServerResponse);

        assertEquals("was toooooo looooong", service.getAccessToken(sessionId));
    }

    @Test
    public void shouldFailWhileGettingAccessToken() throws Exception {
        String sessionId = "123";
        assertNull(service.getAccessToken(sessionId));
    }

    private Account createSimpleAccount(boolean putRefreshTokenDetails, boolean insertAdditionalParams) {
        Map<String, Object> props = new HashMap<>();
        props.put("client_id", "000000004811C246");
        props.put("client_secret", "2KPk-kGUZJVmQXGdyLwZ8PpGHWmKoeSX");
        props.put("code", "code");

        if (insertAdditionalParams) {
            props.put("redirect_uri", "http:%2F%2Fonedrive.toik.pl%2F");
            props.put("root_folder", "folder.fa6a470fcef1fb9a");
        }

        if (putRefreshTokenDetails) {
            props.put("refresh_token", "refresh properties token");
        }

        return new Account("onedrive", "onedrive", props);
    }

    private String loadJSONResponseFromFile(String filename) throws IOException {
        URL filesListUrl = this.getClass().getClassLoader().getResource(filename);
        assert filesListUrl != null;
        return IOUtils.toString(filesListUrl.openStream());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Session> getSessionMap(OnedriveAccountService service) throws NoSuchFieldException, IllegalAccessException {
        Field field = OnedriveAccountServiceImpl.class.getDeclaredField("sessionMap");
        field.setAccessible(true);
        return (Map<String, Session>) field.get(service);
    }

    private Session createSimpleSession() {
        return new Session("", "", "", "", new CloudFile("", new Date(), false, "", "", 0L));
    }
}