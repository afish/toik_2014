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
    private List<String> accountsList;
    private Integer lastAccountId;

    public AccountServiceImpl(){
        accountsList = persistenceService.get("logic", "accounts_list");
        if(accountsList == null){
            accountsList = new ArrayList<>();
        }

        lastAccountId = persistenceService.get("logic", "last_account_id");
        if(lastAccountId == null){
            lastAccountId = 0;
        }
    }

	@Override
	public Account createAccount(String name) {
		logger.info("Creating account: " + name);

        if(name == null){
            throw new IllegalArgumentException("Account name cannot be null");
        }

        if(accountsList.contains(name)){
            throw new IllegalArgumentException("Account with the same name name already exists. Account name: " + name);
        }

        Account account = new Account(Integer.toString(++lastAccountId), name, new HashMap<String, Object>());
        persistenceService.put("logic", "last_account_id", lastAccountId);
        accountsList.add(account.getName());
        persistenceService.put("logic", "accounts_list", accountsList);

        logger.info("Account created: " + account);

        return account;
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Getting all accounts");

        List<Account> result = new ArrayList<>();
        for(String accountName : accountsList){
            result.add((Account)persistenceService.get("logic_account", accountName));
        }

        return result;
	}

	@Override
	public void saveAccount(Account account) {
		logger.info("Saving account: " + account);

        if(account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

		persistenceService.put("logic_account", account.getName(), account);

        logger.info("Account saved: " + account);
	}

	@Override
	public void removeAccount(Account account) {
		logger.info("Removing account: " + account);

        if(account == null){
            throw new IllegalArgumentException("Account cannot be null");
        }

        persistenceService.put("logic_account", account.getName(), null);
        accountsList.remove(account.getName());
        persistenceService.put("logic", "accounts_list", accountsList);

        logger.info("Account removed: " + account);
	}
}
