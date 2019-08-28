package com.cjl.hibernate.demo;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Student;

public class CreateStudentDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			String dob = "1/1/1999";
			Date dateOfBirth = DateUtils.parseDate(dob);
			
			// Make student object
			Student student = new Student("John", "Doe", dateOfBirth, "jdoe@test.com");
			
			// Start transaction
			session.beginTransaction();
			
			// Save student object
			session.save(student);
			
			// Commit transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}

}
