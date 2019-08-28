package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;
import com.cjl.hibernate.entity.Review;

public class GetCoursesAndReviewsDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			// Start transaction
			session.beginTransaction();
			
			// Retrieve instructor from db
			int id = 14;
			Course course = session.get(Course.class, id);
			
			System.out.println("\nCourse: " + course);
			System.out.println("\nReviews: " + course.getReviews());
			
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
