package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	/** Creates account */
	public Account createAccount(String name) {
		logger.info("Creating account: " + name);
		return null;
		// TODO(afish): Implement this.
	}

	/**
	 * Gets list of all account in the system.
	 */
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts");
		return null;
		// TODO(afish): Implement this.
	}

	/** Saves changes made to the account. Adds to allAccount list if was not present. */
	public void saveAccount(Account account) {
		logger.info("Saving account: " + account);
		// TODO(afish): Implement this.
	}

	/** Removes saved account from the system. */
	public void removeAccount(Account account) {
		logger.info("Removing account: " + account);
		// TODO(afish): Implement this.
	}
}
