package de.structuremade.ms.userservice.util.database.repo;

import de.structuremade.ms.userservice.util.database.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, String> {
    List<School> findAllById(String schoolid);
}
