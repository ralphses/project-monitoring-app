package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.user.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SecuredUser, Long> {
    boolean existsByUsername(String username);
    Optional<SecuredUser> findByUsername(String username);

}
