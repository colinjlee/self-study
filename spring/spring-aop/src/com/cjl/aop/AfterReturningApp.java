package com.cjl.aop;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.dao.AccountDAO;

// Executes on a successful return from method call
public class AfterReturningApp {

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		// Call business logic methods
		List<Account> accounts = accountDAO.findAccounts();
		
		System.out.println("\nMain App Accounts: " + accounts);
		
		// Close context
		context.close();
	}

}
