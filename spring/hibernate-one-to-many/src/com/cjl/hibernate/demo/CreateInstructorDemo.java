package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;

public class CreateInstructorDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			// Create objects
			Instructor instructor = new Instructor("John", "Doe", "jdoe@email.com");
			InstructorDetail instructorDetail = new InstructorDetail("http://www.youtube.com/jdoe", "Tennis");
			
			// Associate objects
			instructor.setInstructorDetail(instructorDetail);
			
			// Start transaction
			session.beginTransaction();
			
			// Save instructor
			// Will also save details because of cascade
			session.save(instructor);
			
			// Commit transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
