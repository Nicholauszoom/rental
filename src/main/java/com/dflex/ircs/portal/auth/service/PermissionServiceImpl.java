package com.dflex.ircs.portal.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.auth.entity.Permission;
import com.dflex.ircs.portal.auth.repository.PermissionRepository;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
	private PermissionRepository permissionRepository;
    
    @Override
    public Optional<Permission> findById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Permission findByPermissionName(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName);
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public List<Permission> getPermissionsByRoleId(Long id) {
		return permissionRepository.getPermissionsByRoleId(id);
	}
    
}
