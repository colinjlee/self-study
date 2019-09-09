package com.cjl.spring.dao;

import com.cjl.spring.entity.User;

public interface UserDao {

	User findByUsername(String userName);
	
	void save(User user);
}
