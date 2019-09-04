package com.cjl.springsecurity.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjl.springsecurity.dao.RoleDao;
import com.cjl.springsecurity.dao.UserDao;
import com.cjl.springsecurity.entity.Role;
import com.cjl.springsecurity.entity.User;
import com.cjl.springsecurity.user.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDAO;
	
	@Autowired
	private RoleDao roleDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		return userDAO.findByUsername(userName);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());
		
		if (!crmUser.getFormRole().equals("ROLE_EMPLOYEE")) {
			user.setRoles(Arrays.asList(roleDAO.findRoleByName("ROLE_EMPLOYEE"), roleDAO.findRoleByName(crmUser.getFormRole())));
		} else {
			user.setRoles(Arrays.asList(roleDAO.findRoleByName("ROLE_EMPLOYEE")));
		}
		
		userDAO.save(user);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
