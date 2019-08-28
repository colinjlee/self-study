package com.cjl.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Course;
import com.cjl.hibernate.entity.Instructor;
import com.cjl.hibernate.entity.InstructorDetail;
import com.cjl.hibernate.entity.Review;
import com.cjl.hibernate.entity.Student;

public class CreateCoursesAndStudentsDemo {

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
			
			// Create courses
			Course course = new Course("Math");
			Course course2 = new Course("Science");
			
			// Save courses
			session.save(course);
			session.save(course2);
			
			// Create students
			Student student = new Student("John", "Doe", "jdoe@email.com");
			Student student2 = new Student("Jane", "Doe", "jdoe2@email.com");
			Student student3 = new Student("Joe", "Doe", "jdoe3@email.com");
			Student student4 = new Student("Johnny", "Doe", "jdoe4@email.com");
			Student student5 = new Student("Jessica", "Doe", "jdoe5@email.com");
			
			// Add students to courses
			course.addStudent(student);
			course.addStudent(student2);
			course.addStudent(student3);
			course2.addStudent(student4);
			course2.addStudent(student5);
			course2.addStudent(student3);
			
			// Save students
			session.save(student);
			session.save(student2);
			session.save(student3);
			session.save(student4);
			session.save(student5);
			
			System.out.println("Course1 students: " + course.getStudents());
			System.out.println("Course2 students: " + course2.getStudents());
			
			// Commit transaction
			session.getTransaction().commit();
			
			// Retrieve student from DB and add new courses
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			int studentID = 17;
			Student s = session.get(Student.class, studentID);
			
			Course course3 = new Course("History");
			
			course3.addStudent(s);
			
			session.save(course3);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
