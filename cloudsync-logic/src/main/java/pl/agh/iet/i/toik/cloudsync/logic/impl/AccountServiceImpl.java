package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Override
	public Account createAccount(String name) {
		logger.info("Creating account: " + name);
		return null;
		// TODO(afish): Implement this.
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts");
		return null;
		// TODO(afish): Implement this.
	}

	@Override
	public void saveAccount(Account account) {
		logger.info("Saving account: " + account);
		// TODO(afish): Implement this.
	}

	@Override
	public void removeAccount(Account account) {
		logger.info("Removing account: " + account);
		// TODO(afish): Implement this.
	}
}
