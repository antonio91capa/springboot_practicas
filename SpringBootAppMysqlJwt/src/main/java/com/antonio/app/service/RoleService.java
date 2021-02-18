package com.antonio.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.antonio.app.entity.Role;
import com.antonio.app.enums.RoleEnum;
import com.antonio.app.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public Optional<Role> getByRole(RoleEnum roleName) {
		return roleRepository.findByRole(roleName);
	}

}
