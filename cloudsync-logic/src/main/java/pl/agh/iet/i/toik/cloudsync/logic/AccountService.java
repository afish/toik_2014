package pl.agh.iet.i.toik.cloudsync.logic;

import java.util.List;

/**
 * Service for managing accounts in the system.
 */
public interface AccountService {
	/** Creates account */
	public abstract Account createAccount(String name);

	/**
	 * Gets list of all account in the system.
	 */
	public abstract List<Account> getAllAccounts();

	/** Saves changes made to the account. Adds to allAccount list if was not present. */
	public abstract void saveAccount(Account account);

	/** Removes saved account from the system. */
	public abstract void removeAccount(Account account);
}