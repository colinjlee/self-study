package com.cjl.spring.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjl.spring.entity.User;
import com.cjl.spring.service.UserService;
import com.cjl.spring.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;

//	@Autowired
//	private UserDetailsManager userDetailsManager;
	
//	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Map<String, String> roles;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@PostConstruct
	protected void loadRoles() {
		roles = new LinkedHashMap<>();
		
		roles.put("ROLE_EMPLOYEE", "Employee");
		roles.put("ROLE_MANAGER", "Manager");
		roles.put("ROLE_ADMIN", "Admin");
	}
	
	@GetMapping("/showRegistrationForm")
	public String showLoginPage(Model model) {
		model.addAttribute("crmUser", new CrmUser());
		model.addAttribute("roles", roles);
		
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("crmUser") CrmUser crmUser,
			BindingResult bindingResult,
			Model model) {
		
		String userName = crmUser.getUserName();
		logger.info(">> Processing registration form for: " + userName);
		
		// Form validation
		if (bindingResult.hasErrors()) {
//			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("roles", roles);
			
			return "registration-form";
		}
		
		// Check database if user already exists
		User exists = userService.findByUserName(userName);
		if (exists != null) {
			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("roles", roles);
			model.addAttribute("registrationError", "Username already exists");
			
			logger.warning(">> Username already exists");
			return "registration-form";
		}
		
//		String encodedPassword = passwordEncoder.encode(crmUser.getPassword());
//		encodedPassword = "{bcrypt}" + encodedPassword;
//		
//		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");
//		String formRole = crmUser.getFormRole();
//		if (!formRole.equals("ROLE_EMPLOYEE")) {
//			authorities.add(new SimpleGrantedAuthority(formRole));
//		}
//		
//		User user = new User(userName, encodedPassword, authorities);
//		
//		userDetailsManager.createUser(user);
		
		userService.save(crmUser);
		
		logger.info(">> Successfully created user: " + userName);
		return "registration-confirmation";
	}
	
//	private boolean userExists(String userName) {
//		boolean exists = userDetailsManager.userExists(userName);
//		
//		return exists;
//	}
	
}
