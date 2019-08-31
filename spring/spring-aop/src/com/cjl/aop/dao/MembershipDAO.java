package com.cjl.aop.dao;

import org.springframework.stereotype.Component;

@Component
public class MembershipDAO {

	public boolean addMember() {
		System.out.println(getClass() + ": adding account");
		return true;
	}
	
	public boolean pay() {
		System.out.println(getClass() + ": paying membership fee");
		return true;
	}
}
