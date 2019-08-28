package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;

public class CreateCoursesAndReviewsDemo {

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
			// Start transaction
			session.beginTransaction();
			
			// Retrieve instructor from db
			int id = 1;
			Instructor instructor = session.get(Instructor.class, id);
			
			// Create courses
			Course course = new Course("Math");
			Course course2 = new Course("Science");
			Course course3 = new Course("History");
			Course course4 = new Course("Literature");
			
			// Add courses to instructor
			instructor.add(course);
			instructor.add(course2);
			instructor.add(course3);
			instructor.add(course4);			
			
			// Save courses
			session.save(course);
			session.save(course2);
			session.save(course3);
			session.save(course4);
			
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
