package com.cjl.aop;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.dao.AccountDAO;

// Executes regardless of success or failure. Should work for both
// After advice executes before AfterThrowing advice
public class AfterApp {

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		// Call business logic methods
		try {
			boolean test = false;
			
			List<Account> accounts = accountDAO.findAccounts(test);
		} catch (Exception e) {
			System.out.println("\nMain app: catching exception " + e);
		}
		
		// Close context
		context.close();
	}

}
