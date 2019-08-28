package com.cjl.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Student;

public class ReadStudentDemo {

	public static void main(String[] args) {
		// Create session factory
		// Configure defaults to looking for a file named "hibernate.cfg.xml" if no arg
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		// Create session
		Session session = factory.getCurrentSession();
		
		try {
			
			// Start transaction
			session.beginTransaction();
			
			// Query all students
			List<Student> studentList = session.createQuery("FROM Student").getResultList();
			
			// Display students
			System.out.println("\nAll students: " + studentList.size());
			printStudents(studentList);
			
			// Query students with last name Doe
			studentList = session.createQuery("FROM Student s WHERE s.lastName='Doe'").getResultList();
			
			System.out.println("\nStudents with last name Doe: " + studentList.size());
			printStudents(studentList);
			
			// Query students with last name Simpson or first name John
			studentList = session.createQuery("FROM Student s WHERE s.lastName='Simpson' OR s.firstName='John'").getResultList();
			
			System.out.println("\nStudents with first name John or last name Simpson: " + studentList.size());
			printStudents(studentList);
			
			// Query students where email ends with test.com
			studentList = session.createQuery("FROM Student s WHERE s.email LIKE '%test.com'").getResultList();
			
			System.out.println("\nStudents with emails ending with test.com: " + studentList.size());
			printStudents(studentList);
			
			// Query students where email ends with gmail.com
			studentList = session.createQuery("FROM Student s WHERE s.email LIKE '%gmail.com'").getResultList();
			
			System.out.println("\nStudents with emails ending with gmail.com: " + studentList.size());
			printStudents(studentList);
			
			// Commit transaction
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}
	
	public static void printStudents(List<Student> list) {
		if (list == null || list.size() == 0) {
			System.out.println("No students");
			return;
		}
		
		for (Student s : list) {
			System.out.println(s);
		}
	}

}
