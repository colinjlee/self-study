package com.cjl.spring.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cjl.spring.entity.User;
import com.cjl.spring.user.CrmUser;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);
	
	void save(CrmUser crmUser);
}
