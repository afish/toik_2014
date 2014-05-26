package pl.agh.iet.i.toik.cloudsync.logic.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.agh.iet.i.toik.cloudsync.logic.Account;

public class AccountServiceImplTest {
	private AccountServiceImpl service;

	@Before
	public void setUp() {
		service = new AccountServiceImpl();
		service.setPersistenceService(new FakePersistenceService());
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
