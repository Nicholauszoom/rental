package com.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ircs.portal.setup.entity.Role;
import com.ircs.portal.setup.repository.RoleRepository;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
	private RoleRepository roleRepository;
    
    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role findByRoleNameAndNotId(String roleName, Long id) {
		return roleRepository.findByRoleNameAndNotId(roleName,id);
	}

	@Override
	public boolean roleReadyForDeletion(Long id) {
		return roleRepository.roleReadyForDeletion(id);
	}
    
}
