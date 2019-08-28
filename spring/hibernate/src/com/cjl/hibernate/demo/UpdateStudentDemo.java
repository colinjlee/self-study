package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Student;

public class UpdateStudentDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			int studentId = 1;
			
			// Start transaction
			session.beginTransaction();
			
			// Retrieve student based on id
			Student student = session.get(Student.class, studentId);
			
			// Update student
			student.setFirstName("Marge");
			student.setLastName("Simpson");
			
			// Commit transaction
			session.getTransaction().commit();
			
			// Bulk updating
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			session.createQuery("UPDATE Student s SET email='updated2@gmail.com' WHERE s.lastName='Simpson'").executeUpdate();
			
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

}
