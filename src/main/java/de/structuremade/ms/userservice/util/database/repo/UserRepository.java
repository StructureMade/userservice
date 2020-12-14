package de.structuremade.ms.userservice.util.database.repo;

import de.structuremade.ms.userservice.util.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByToken(String s);
}
