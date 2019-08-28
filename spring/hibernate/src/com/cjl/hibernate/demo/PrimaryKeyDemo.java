package com.cjl.hibernate.demo;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Student;

public class PrimaryKeyDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			// Make students
			Student student = new Student("Jane", "Doe", DateUtils.parseDate("01/02/2003"), "jdoe2@test.com");
			Student student2 = new Student("Mary", "Doe", DateUtils.parseDate("05/12/2002"), "mdoe@test.com");
			Student student3 = new Student("Bob", "Doe", DateUtils.parseDate("15/06/2003"), "bdoe@test.com");
			
			// Start transaction
			session.beginTransaction();
			
			// Save student object
			session.save(student);
			session.save(student2);
			session.save(student3);
			
			// Commit transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}

	}

}
