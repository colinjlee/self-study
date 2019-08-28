package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;
import com.cjl.hibernate.entity.Review;

public class CreateCoursesDemo {

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
			
			// Create courses
			Course course = new Course("Cooking");
			Course course2 = new Course("Knitting");
			
			// Add reviews
			course.addReview(new Review("Learned to cook burgers"));
			course.addReview(new Review("Dumb course"));
			course.addReview(new Review("Useful and practical"));
			course2.addReview(new Review("Course for grannies"));
			
			// Cascade save courses and reviews
			session.save(course);
			session.save(course2);
			
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
