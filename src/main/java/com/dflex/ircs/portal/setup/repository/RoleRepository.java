package com.dflex.ircs.portal.setup.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dflex.ircs.portal.setup.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findById(Long id);
	
	@Query("from Role where upper(trim(roleName)) =:roleName")
	public Role findByRoleName(@Param("roleName")String roleName);

	@Query("from Role where upper(trim(roleName)) =:roleName and id !=:id ")
	public Role findByRoleNameAndNotId(@Param("roleName")String roleName,@Param("id") Long id);

	@Query(value="select case when count(ur) = 0 then true else false end from tab_user_role ur,users u where ur.role_id =:id and ur.user_id = u.id and u.status_id = 1",nativeQuery=true)
	public boolean roleReadyForDeletion(Long id);
}
