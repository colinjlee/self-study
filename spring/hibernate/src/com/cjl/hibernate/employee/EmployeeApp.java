package com.cjl.hibernate.employee;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cjl.hibernate.entity.Employee;

public class EmployeeApp {

	public static void main(String[] args) {
		// Session factory
		SessionFactory factory = new Configuration()
				.configure("hibernate.employee.cfg.xml")
				.addAnnotatedClass(Employee.class)
				.buildSessionFactory();
		
		// Session
		Session session = factory.getCurrentSession();
		
		// CRUD
		try {
			int id = 1;
//			/*
			// -------------- Create
			Employee employee = new Employee("John", "Doe", "jdoe@test.com", "Google");
			Employee employee2 = new Employee("Jane", "Doe", "jdoe2@test.com", "Google");
			Employee employee3 = new Employee("Homer", "Simpson", "hsimpson@gmail.com", "Apple");
			Employee employee4 = new Employee("Marge", "Simpson", "msimpson@yahoo.com", "Amazon");
			Employee employee5 = new Employee("Bob", "Smith", "bsmith@email.com", "Disney");
			
//			/*
			session.beginTransaction();
			
			session.save(employee);
			session.save(employee2);
			session.save(employee3);
			session.save(employee4);
			session.save(employee5);
			
			session.getTransaction().commit();
//			*/
			
//			 /*
			// -------------- Read
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// Get single employee from id
			Employee employeeFromTable = session.get(Employee.class, employee.getId());
			
			// Query all employees
			List<Employee> employeeList = session.createQuery("FROM Employee").getResultList();
			
			System.out.println("\nAll employees: " + employeeList.size());
			printList(employeeList);
			
			// Query employees with last name Simpson
			employeeList = session.createQuery("FROM Employee s WHERE s.lastName='Simpson'").getResultList();
			
			System.out.println("\nAll employees with last name Simpson: " + employeeList.size());
			printList(employeeList);
			
			// Query employees working at google
			employeeList = session.createQuery("FROM Employee s WHERE s.company='Google'").getResultList();
			
			System.out.println("\nAll employees working at Google: " + employeeList.size());
			printList(employeeList);
			
			session.getTransaction().commit();
//			*/
			
//			/*
			// -------------- Update
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// Get single employee from id
			Employee employeeFromTable2 = session.get(Employee.class, id);
			
			// Updating single employee through object
			employeeFromTable2.setFirstName("Updated_First_Name");
			
			session.getTransaction().commit();
			
			// Updating multiple through query
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			session.createQuery("UPDATE Employee SET email='changed@gmail.com' WHERE email LIKE '%gmail.com'").executeUpdate();
			
			session.getTransaction().commit();
//			*/
			
//			/*
			// -------------- Delete
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// Get single employee from id
			Employee employeeFromTable3 = session.get(Employee.class, id);
			
			// Delete through object
			session.delete(employeeFromTable3);

			session.getTransaction().commit();
			
			// Delete through query
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			session.createQuery("DELETE FROM Employee WHERE id=1").executeUpdate();
			
			session.getTransaction().commit();
//			*/
		} finally {
			factory.close();
		}

	}
	
	private static void printList(List<Employee> list) {
		if (list == null || list.size() == 0) {
			System.out.println("No employees");
			return;
		}
		
		for (Employee e : list) {
			System.out.println(e);
		}
	}

}
