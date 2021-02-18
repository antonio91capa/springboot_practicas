package com.antonio.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.antonio.app.entity.Role;
import com.antonio.app.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByRole(RoleEnum roleName);

}
