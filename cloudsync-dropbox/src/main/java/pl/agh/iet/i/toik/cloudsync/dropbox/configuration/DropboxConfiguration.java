package pl.agh.iet.i.toik.cloudsync.dropbox.configuration;

import java.io.Serializable;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

@SuppressWarnings("serial")
public class DropboxConfiguration implements Serializable {

    protected static final String APP_KEY = "hn2bx52zyvavui7";
    protected static final String APP_SECRET = "coqhmdex9jgg5bs";
    protected static final String APP_NAME = "TOiK-DbxCloud";

    private DbxWebAuthNoRedirect webAuth;
    private DbxRequestConfig config;
    private DbxAppInfo appInfo;

    public DropboxConfiguration() {
        this.appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        this.config = new DbxRequestConfig(APP_NAME, Locale.getDefault().toString());
        this.webAuth = new DbxWebAuthNoRedirect(this.config, this.appInfo);
    }

    /**
     * Fetches Dropbox WebAuth object.
     *
     * @return Dropbox authorization object
     */
    public DbxWebAuthNoRedirect getWebAuth() {
        return webAuth;
    }

    /**
     * Fetches Dropbox config.
     *
     * @return Dropbox configuration object
     */
    public DbxRequestConfig getConfig() {
        return config;
    }

    /**
     * Fetches Dropbox application info.
     *
     * @return Dropbox application info
     */
    public DbxAppInfo getAppInfo() {
        return appInfo;
    }

}
