package com.cjl.springsecurity.dao;

import com.cjl.springsecurity.entity.User;

public interface UserDao {

	User findByUsername(String userName);
	
	void save(User user);
}
