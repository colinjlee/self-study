package com.cjl.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cjl.aop.dao.AccountDAO;
import com.cjl.aop.dao.MembershipDAO;

// Executes before the method call
public class BeforeApp {

	public static void main(String[] args) {
		
		// Read spring config class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		// Get bean from spring container
		AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);
		MembershipDAO membershipDAO = context.getBean("membershipDAO", MembershipDAO.class);
		
		// Call business logic methods
		// Account methods
		Account acc = new Account();
		acc.setName("John");
		acc.setLevel("Diamond");
		accountDAO.addAccount(acc, true);
		System.out.println();
		
		accountDAO.doWork();
		System.out.println();
		
		// Member methods
		membershipDAO.addMember();
		System.out.println();
		
		membershipDAO.pay();
		
		// Close context
		context.close();
	}

}
