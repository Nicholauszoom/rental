package com.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.ircs.portal.setup.entity.Permission;


public interface PermissionService {
	
    public Optional<Permission> findById(Long id);
	
	public Permission findByPermissionName(String permissionName);	

    public Permission savePermission(Permission permission);

	public List<Permission> findAll();

	public List<Permission> getPermissionsByRoleId(Long id);
}
