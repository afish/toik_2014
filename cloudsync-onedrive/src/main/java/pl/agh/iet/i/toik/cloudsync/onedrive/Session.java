package pl.agh.iet.i.toik.cloudsync.onedrive;

import org.joda.time.DateTime;
import pl.agh.iet.i.toik.cloudsync.logic.CloudFile;

public class Session {
    private DateTime tokenExpirationDate;

    private String accessToken;

    private String clientId;

    private String refreshToken;

    private String redirectURI;

    private String clientSecret;

    private CloudFile rootFolder;

    public Session(String clientId, String refreshToken, String redirectURI, String clientSecret, CloudFile rootFolder) {
        this.clientId = clientId;
        this.refreshToken = refreshToken;
        this.redirectURI = redirectURI;
        this.clientSecret = clientSecret;
        this.rootFolder = rootFolder;
    }

    public DateTime getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(DateTime tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public CloudFile getRootFolder() {
        return rootFolder;
    }
}
