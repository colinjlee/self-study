package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;

public class DeleteDemo {

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
			
			// Get instructor by id
			int id = 1;
			Instructor instructor = session.get(Instructor.class, id);
					
			// Delete instructor
			// Also deletes details because of cascade all
			if (instructor != null) {
				session.delete(instructor);
			}
			
			// Deleting instructor detail without deleting instructor
			// Need to have cascade delete off
			// Need to set link back to detail to null
//			InstructorDetail detail = session.get(InstructorDetail.class, id2);
//			
//			detail.getInstructor().setInstructorDetail(null);
//			
//			session.delete(detail);
			
			// Commit transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}

}
