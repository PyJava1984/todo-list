package com.smefinance.test.core.repository;

import java.util.Optional;

import com.smefinance.test.core.enumeration.ERole;
import com.smefinance.test.core.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
