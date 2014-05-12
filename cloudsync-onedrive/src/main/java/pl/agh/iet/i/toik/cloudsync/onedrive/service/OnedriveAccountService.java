package pl.agh.iet.i.toik.cloudsync.onedrive.service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;

public interface OnedriveAccountService {
    String login(Account account);

    void logout(String sessionId);

    String getAccessToken(String sessionId);
}
