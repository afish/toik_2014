package pl.agh.iet.i.toik.cloudsync.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.agh.iet.i.toik.cloudsync.logic.Account;
import pl.agh.iet.i.toik.cloudsync.logic.AccountService;
import pl.agh.iet.i.toik.cloudsync.logic.PersistenceService;

@Service
public class AccountServiceImpl implements AccountService {
	private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private PersistenceService persistenceService;

    private List<String> loadAccountsList(){
        List<String> accountsList = (List) persistenceService.get("logic", "accounts_list");
        if(accountsList == null){
            accountsList = new ArrayList<>();
        }

        return accountsList;
    }

    private void saveAccountsList(List<String> accountsList){
        ArrayList<String> accountsArrayList = new ArrayList<>(accountsList);
        persistenceService.put("logic", "accounts_list", accountsArrayList);
    }

	@Override
	public Account createAccount(String name) {
        if(name == null){
            throw new IllegalArgumentException("Account name cannot be null");
        }

		logger.info("Creating account: " + name);

        List<String> accountsList = loadAccountsList();
        if(accountsList.contains(name)){
            throw new IllegalArgumentException("Account with the same name name already exists. Account name: " + name);
        }

        Integer accountId = persistenceService.getNextFromSequence("logic", "account_id");
        Account account = new Account(Integer.toString(accountId), name, new HashMap<String, Object>());
        persistenceService.put("logic", "account_" + name, account);

        accountsList.add(account.getName());
        saveAccountsList(accountsList);

        logger.info("Account created: " + account);

        return account;
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts");

        List<Account> result = new ArrayList<>();

        for(String accountName : loadAccountsList()){
            result.add((Account)persistenceService.get("logic", "account_" + accountName));
        }

        return result;
	}

	@Override
	public void saveAccount(Account account) {
        if(account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

		logger.info("Saving account: " + account);

		persistenceService.put("logic", "account_" + account.getName(), account);

        logger.info("Account saved: " + account);
	}

	@Override
	public void removeAccount(Account account) {
        if(account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

		logger.info("Removing account: " + account);

        List<String> accountsList = loadAccountsList();
        accountsList.remove(account.getName());
        saveAccountsList(accountsList);

        persistenceService.remove("logic", "account_" + account.getName());

        logger.info("Account removed: " + account);
	}
}
