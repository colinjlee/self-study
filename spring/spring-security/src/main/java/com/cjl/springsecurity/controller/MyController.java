package com.cjl.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

	@GetMapping("/")
	public String showHome() {
		return "home";
	}
	
	@GetMapping("/employees")
	public String showEmployeesPage() {
		return "employees";
	}
	
	@GetMapping("/managers")
	public String showManagersPage() {
		return "managers";
	}
	
	@GetMapping("/admins")
	public String showAdminsPage() {
		return "admins";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "access-denied";
	}
}
