package com.cjl.springdemo.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

	// Show initial HTML form
	@RequestMapping("/showForm")
	public String showForm() {
		return "helloworld-form";
	}
	
	// Process the form
	@RequestMapping("/processForm")
	public String processForm() {
		return "helloworld";
	}
	
	// Read form data and add data to model
	@RequestMapping("/processFormV2")
	public String toUpperCaseName(HttpServletRequest request, Model model) {
		// Read request parameter from HTML form
		String name = request.getParameter("studentName");
		
		// Convert name to upper case
		name = name.toUpperCase();
		
		// Add to model
		model.addAttribute("upperCaseName", name);
		
		return "helloworld";
	}
	
	@RequestMapping("/processFormV3")
	public String processFormV3(
			@RequestParam("studentName") String name, 
			Model model) {
		
		// Convert name to upper case
		name = "Hey " + name + ", how are you doing?";
		
		// Add to model
		model.addAttribute("upperCaseName", name);
		
		return "helloworld";
	}
}
