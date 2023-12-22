package com.ircs.portal.setup.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ircs.portal.setup.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	public Optional<Permission> findById(Long id);
	
	public Permission findByPermissionName(String permissionName);

	@Query(value="Select p.* from tab_permission p, tab_role_permission rp where p.id = rp.permission_id and rp.role_id =:roleId ", nativeQuery=true)
	public List<Permission> getPermissionsByRoleId(@Param("roleId")Long roleId);
	
}