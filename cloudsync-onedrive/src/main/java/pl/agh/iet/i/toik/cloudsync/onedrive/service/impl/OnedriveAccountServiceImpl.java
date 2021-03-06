package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;
import pl.agh.iet.i.toik.cloudsync.onedrive.Session;
import pl.agh.iet.i.toik.cloudsync.onedrive.service.OnedriveAccountService;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Service
public class OnedriveAccountServiceImpl implements OnedriveAccountService {
    private static Logger logger = LoggerFactory.getLogger(OnedriveAccountServiceImpl.class);

    @Autowired
    private Client client;

    private Map<String, Session> sessionMap = new HashMap<>();

    @Override
    public String login(Account account) {
        String clientId = (String) account.getPropertyList().get("client_id");
        String clientSecret = (String) account.getPropertyList().get("client_secret");
        String redirectURI = (String) account.getPropertyList().get("redirect_uri");
        String refreshToken = (String) account.getPropertyList().get("refresh_token");
        String code = (String) account.getPropertyList().get("code");
        String rootFolderName = (String) account.getPropertyList().get("root_folder");
        if (redirectURI == null) {
            logger.debug("Redirect URI not provided - using default");
            redirectURI = "https:%2F%2Flogin.live.com%2Foauth20_desktop.srf";
        }
        if (rootFolderName == null) {
            logger.debug("Root folder not provided - using default");
            rootFolderName = "me/skydrive";
        }
        CloudFile rootFolder = new CloudFile("/", null, true, "/", rootFolderName, 0L);
        if (clientId == null || clientSecret == null || (refreshToken == null && code == null)) {
            logger.warn("Account has not enough data to login: returning null");
            return null;
        }
        Session session = new Session(clientId, refreshToken, redirectURI, clientSecret, rootFolder);
        if (refreshToken == null) {
            if (getInitialTokens(session, code) == null) {
                logger.warn("Unable to get access and refresh token from remote service: returning null");
                return null;
            } else {
                logger.debug("Saving acquired refresh token to account properties");
                account.getPropertyList().put("refresh_token", session.getRefreshToken());
            }
        } else if (refreshAccessToken(session) == null) {
            logger.warn("Unable to get access token from remote service: returning null");
            return null;
        }
        String sessionId = clientId + session.getTokenExpirationDate();
        sessionMap.put(sessionId, session);
        logger.info("Access token granted: token expires in {}, sessionId={}", session.getTokenExpirationDate(),
                sessionId);
        return sessionId;
    }

    @Override
    public void logout(String sessionId) {
        logger.trace("Logging out: sessionId={}", sessionId);
        if (sessionMap.remove(sessionId) == null) {
            logger.warn("Session not found: can't logout");
        } else {
            logger.info("Successful logout, sessionId={}", sessionId);
        }
    }

    @Override
    public String getAccessToken(String sessionId) {
        Session session = sessionMap.get(sessionId);
        if (session != null) {
            String accessToken = session.getAccessToken();
            DateTime tokenExpirationDate = session.getTokenExpirationDate();
            if (accessToken == null || tokenExpirationDate == null) {
                logger.warn("Token or token expiration date is null: trying to get one");
            } else if (tokenExpirationDate.minusMinutes(5).isAfter(new DateTime())) {
                logger.trace("Returning session access token");
                return session.getAccessToken();
            } else {
                logger.debug("Token has expired, trying to get a new one");
            }
            if (refreshAccessToken(session) != null) {
                return session.getAccessToken();
            } else {
                logger.warn("Unable to get access token from remote service: returning null");
                return null;
            }
        }
        logger.warn("Session not found: returning null");
        return null;
    }

    @Override
    public Session getSession(String sessionId) {
        logger.trace("Returning session: sessionId={}", sessionId);
        return sessionMap.get(sessionId);
    }

    private String getInitialTokens(Session session, String code) {
        WebResource webResource = client
                .resource("https://login.live.com/oauth20_token.srf")
                .queryParam("client_id", session.getClientId())
                .queryParam("client_secret", session.getClientSecret())
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", session.getRedirectURI())
                .queryParam("code", code);


        JSONObject json = getTokenRequestOutput(webResource);
        if (json == null) {
            return null;
        }

        try {
            session.setAccessToken(json.get("access_token").toString());
            session.setRefreshToken(json.get("refresh_token").toString());
            session.setTokenExpirationDate(new DateTime().plusHours(1));
        } catch (JSONException e) {
            logger.error("Error while processing response from server - {}", e.getMessage());
            return null;
        }

        logger.debug("Access token successfully gathered from remote service");
        return session.getAccessToken();
    }

    private String refreshAccessToken(Session session) {
        WebResource webResource = client
                .resource("https://login.live.com/oauth20_token.srf")
                .queryParam("client_id", session.getClientId())
                .queryParam("client_secret", session.getClientSecret())
                .queryParam("grant_type", "refresh_token")
                .queryParam("redirect_uri", session.getRedirectURI())
                .queryParam("refresh_token", session.getRefreshToken());

        JSONObject json = getTokenRequestOutput(webResource);
        if (json == null) {
            return null;
        }

        try {
            session.setAccessToken(json.get("access_token").toString());
            session.setTokenExpirationDate(new DateTime().plusHours(1));
        } catch (JSONException e) {
            logger.error("Error while processing response from server - {}", e.getMessage());
            return null;
        }

        logger.debug("Access token successfully gathered from remote service");
        return session.getAccessToken();
    }

    private JSONObject getTokenRequestOutput(WebResource webResource) {
        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            logger.warn("Remote service returned HTTP error code: {}, returning null", response.getStatus());
            return null;
        }
        String output = response.getEntity(String.class);

        logger.trace("Response from remote service: {}", output);

        return new JSONObject(output);
    }
}
