package de.structuremade.ms.userservice.util.database.repo;

import de.structuremade.ms.userservice.util.database.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionsRepository extends JpaRepository<Permissions, String> {
}
