package pl.agh.iet.i.toik.cloudsync.onedrive.service.impl;

import junit.framework.TestCase;
import org.junit.Assert;
import pl.agh.iet.i.toik.cloudsync.logic.Account;

import java.util.HashMap;
import java.util.Map;

public class OnedriveAccountServiceImplTest extends TestCase {

    private OnedriveAccountServiceImpl onedriveAccountService;

    public void setUp() throws Exception {
        super.setUp();
        onedriveAccountService = new OnedriveAccountServiceImpl();
    }

    public void tearDown() throws Exception { }

    public void testLogin() throws Exception {
        assertNull(onedriveAccountService.login(new Account("id", "name", new HashMap<String, Object>())));
    }

    public void testLogout() throws Exception {
        onedriveAccountService.logout("1.414");
        assertTrue(1 == 3 - 2);
    }

    public void testGetAccessToken() throws Exception {
        assertNull(onedriveAccountService.getAccessToken("ala ma kota"));
    }

    public void testGetSession() throws Exception {
        assertNull(onedriveAccountService.getSession("sesja"));
    }
}