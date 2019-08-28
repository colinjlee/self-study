package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;

public class BidirectionalDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			// Start transaction
			session.beginTransaction();
			
			// Get instructor
			int id = 2;
			Instructor instructor = session.get(Instructor.class, id);
			
			// Get details through instructor
			InstructorDetail instructorDetail = instructor.getInstructorDetail();
			
			System.out.println("\nInstructor: " + instructor + "\nDetails: " + instructorDetail);
			
			// Get instructor details
			int detailID = 3;
			InstructorDetail instructorDetail2 = session.get(InstructorDetail.class, detailID);
			
			// Get instructor through details
			Instructor instructor2 = instructorDetail2.getInstructor();
			
			System.out.println("\nInstructor: " + instructor2 + "\nDetails: " + instructorDetail2);

			
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
