package pl.agh.iet.i.toik.cloudsync.onedrive.service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.onedrive.Session;

public interface OnedriveAccountService {
    String login(Account account);

    void logout(String sessionId);

    String getAccessToken(String sessionId);

    Session getSession(String sessionId);
}
