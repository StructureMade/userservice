package de.structuremade.ms.userservice.util.database.repo;

import de.structuremade.ms.userservice.util.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
