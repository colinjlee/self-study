package com.cjl.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;
import com.cjl.hibernate.entity.Review;
import com.cjl.hibernate.entity.Student;

public class DeleteCourseAndStudentDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Student.class)
				.addAnnotatedClass(Review.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			// Start transaction
			session.beginTransaction();
			
			// Retrieve the course
			int courseID = 21;
			Course course = session.get(Course.class, courseID);
			
			// Delete the course
			if (course != null) {
				session.delete(course);
			}
			
			// Commit
			session.getTransaction().commit();
			
			// Check students were not deleted
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Student> students = session.createQuery("FROM Student").getResultList();
			System.out.println("\nStudents: " + students);
			
			for (Student s : students) {
				System.out.println(s.getFirstName() + " " + s.getLastName() + ", courses: " + s.getCourses());
			}
			
			session.getTransaction().commit();
			
			// Delete student and check courses were not deleted
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			int studentID = 17;
			Student s = session.get(Student.class, studentID);
			
			if (s != null) {
				session.delete(s);
			}
			
			List<Course> courses = session.createQuery("FROM Course").getResultList();
			System.out.println("\nCourses: " + courses);
			
			for (Course c : courses) {
				System.out.println("Course: " + c.getTitle() + ", students: " + c.getStudents());
			}
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
