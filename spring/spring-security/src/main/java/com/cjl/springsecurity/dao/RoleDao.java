package com.cjl.springsecurity.dao;

import com.cjl.springsecurity.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String roleName);
}
