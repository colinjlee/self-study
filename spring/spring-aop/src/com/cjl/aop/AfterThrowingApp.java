package com.cjl.aop;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.dao.AccountDAO;

// Executes after throwing an exception
public class AfterThrowingApp {

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		// Call business logic methods
		try {
			boolean test = true;
			
			List<Account> accounts = accountDAO.findAccounts(test);
		} catch (Exception e) {
			System.out.println("\nMain app: catching exception " + e);
		}
		
		// Close context
		context.close();
	}

}
