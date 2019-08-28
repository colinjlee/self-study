package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;

public class FetchJoinDemo {

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

			Query<Instructor> query = session.createQuery("SELECT i FROM Instructor i JOIN FETCH i.courses WHERE i.id=:instructorId", Instructor.class);
			query.setParameter("instructorId", id);
			
			Instructor instructor = query.getSingleResult();
			
			System.out.println("\nInstructor: " + instructor);
			
			// Commit transaction
			session.getTransaction().commit();
			
			session.close();
			
			System.out.println("Courses: " + instructor.getCourses());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
