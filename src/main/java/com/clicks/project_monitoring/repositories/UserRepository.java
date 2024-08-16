package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.user.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SecuredUser, Long> {
    boolean existsByUsername(String username);
    Optional<SecuredUser> findByUsername(String username);

    @Query(value = """
            (SELECT name FROM admin WHERE user_id = :projectReference)
            UNION
            (SELECT name FROM student WHERE user_id = :projectReference)
            UNION
            (SELECT name FROM supervisor WHERE user_id = :projectReference)
            LIMIT 1
            """,
            nativeQuery = true)
    String findUserName(@Param("projectReference") String projectReference);


}
