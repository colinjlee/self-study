package com.cjl.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cjl.springsecurity.entity.User;
import com.cjl.springsecurity.user.CrmUser;

public interface UserService extends UserDetailsService {

	User findByUserName(String userName);
	
	void save(CrmUser crmUser);
}
