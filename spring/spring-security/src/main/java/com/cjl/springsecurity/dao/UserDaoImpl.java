package com.cjl.springsecurity.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cjl.springsecurity.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public User findByUsername(String userName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> query = currentSession.createQuery("FROM User WHERE userName=:uName", User.class);
		query.setParameter("uName", userName);
		
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (Exception e) {
			user = null;
		}
		
		return user;
	}

	@Override
	public void save(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		currentSession.saveOrUpdate(user);
	}
	
}
