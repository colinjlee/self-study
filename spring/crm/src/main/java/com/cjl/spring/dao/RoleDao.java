package com.cjl.spring.dao;

import com.cjl.spring.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String roleName);
}
