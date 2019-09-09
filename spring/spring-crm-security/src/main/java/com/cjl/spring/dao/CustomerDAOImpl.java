package com.cjl.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cjl.spring.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<Customer> getCustomers() {

		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = session.createQuery("FROM Customer ORDER BY lastName", Customer.class);
		
		List<Customer> customers = query.getResultList();
		
		return customers;
	}

	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		// Insert new customer if id doesn't exist, or update if it does
		session.saveOrUpdate(customer);
		
	}

	public Customer getCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(Customer.class, id);
	}

	public void deleteCustomer(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		Query<Customer> query = session.createQuery("DELETE FROM Customer WHERE id=:customerId");
		query.setParameter("customerId", id);
		query.executeUpdate();
	}

	public List<Customer> searchCustomers(String searchName) {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> query = null;
		
		if (searchName == null || searchName.trim().length() == 0) {
			query = session.createQuery("FROM Customer ORDER BY lastName", Customer.class);
		} else {
			query = session.createQuery("FROM Customer WHERE lower(firstName) like :theName OR lower(lastName) like :theName ORDER BY lastName", Customer.class);
			query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
		}
				
		return query.getResultList();
	}

}
