package pl.agh.iet.i.toik.cloudsync.logic.impl;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

public class AccountServiceImplTest {
	private AccountServiceImpl service;
	private Map<String, Object> accounts;

	@Before
	public void setUp() {
		service = new AccountServiceImpl();
		accounts = new HashMap<>();
		service.setPersistenceService(new PersistenceService() {

			@Override
			public void remove(String prefix, String id) {
				accounts.remove(prefix + id);
			}

			@Override
			public <T extends Serializable> void put(String prefix, String id,
					T obj) {
				accounts.put(prefix + id, obj);
			}

			@Override
			public boolean has(String prefix, String id) {
				return accounts.containsKey(prefix + id);
			}

			@Override
			public int getNextFromSequence(String prefix, String sequenceName) {
				return 0;
			}

			@Override
			public <T extends Serializable> T get(String prefix, String id) {
				return (T) accounts.get(prefix + id);
			}
		});
	}

	@Test
	public void testCreateAccount() {
		service.createAccount("Piotr");

		assertEquals(1, service.getAllAccounts().size());
	}

	@Test
	public void testCreateTwoAccounts() {
		service.createAccount("Piotr");
		service.createAccount("Michał");

		assertEquals(2, service.getAllAccounts().size());
	}

	@Test
	public void testSaveAccount() {
		Account account = service.createAccount("Piotr");

		assertEquals(1, service.getAllAccounts().size());
		service.saveAccount(account);
		assertEquals(1, service.getAllAccounts().size());
	}

	@Test
	public void testRemoveAccount() {
		Account account = service.createAccount("Piotr");

		assertEquals(1, service.getAllAccounts().size());
		service.removeAccount(account);
		assertEquals(0, service.getAllAccounts().size());
	}
}
