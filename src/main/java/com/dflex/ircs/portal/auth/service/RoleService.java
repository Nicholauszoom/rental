package com.dflex.ircs.portal.auth.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.auth.entity.Role;


public interface RoleService {
	
    public Optional<Role> findById(Long id);
	
	public Role findByRoleName(String roleName);	

    public Role saveRole(Role role);

	public List<Role> findAll();

	public Role findByRoleNameAndNotId(String roleName,Long id);

	public boolean roleReadyForDeletion(Long id);
}
