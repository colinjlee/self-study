package com.cjl.aop.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cjl.aop.Account;

@Component
public class AccountDAO {
	
	public List<Account> findAccounts() {
		List<Account> accounts = new ArrayList<>();
		
		Account acc1 = new Account("John", "Silver");
		Account acc2 = new Account("Jane", "Gold");
		Account acc3 = new Account("Josh", "Platinum");
		
		accounts.add(acc1);
		accounts.add(acc2);
		accounts.add(acc3);
		
		return accounts;
	}
	
	public List<Account> findAccounts(boolean bool) {
		// Throw exception if bool is true
		if (bool) {
			throw new RuntimeException("After throwing test exception");
		}
		
		List<Account> accounts = new ArrayList<>();
		
		Account acc1 = new Account("John", "Silver");
		Account acc2 = new Account("Jane", "Gold");
		Account acc3 = new Account("Josh", "Platinum");
		
		accounts.add(acc1);
		accounts.add(acc2);
		accounts.add(acc3);
		
		return accounts;
	}

	public void addAccount(Account account, boolean vip) {
		System.out.println(getClass() + ": adding account");
	}
	
	public boolean doWork() {
		System.out.println(getClass() + ": do work");
		return true;
	}
	
}
