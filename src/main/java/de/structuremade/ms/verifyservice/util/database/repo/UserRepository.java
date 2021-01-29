package de.structuremade.ms.verifyservice.util.database.repo;

import de.structuremade.ms.verifyservice.util.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByToken(String Token);

    boolean existsByToken(String Token);

    boolean existsByEmail(String email);
}
