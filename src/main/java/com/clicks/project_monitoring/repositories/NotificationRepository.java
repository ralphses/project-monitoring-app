package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
            SELECT IF(
            (EXISTS (SELECT 1 FROM admin WHERE user_id = :sender) OR
             EXISTS (SELECT 1 FROM student WHERE user_id = :sender) OR
             EXISTS (SELECT 1 FROM supervisor WHERE user_id = :sender)) AND
            (EXISTS (SELECT 1 FROM admin WHERE user_id = :receiver) OR
             EXISTS (SELECT 1 FROM student WHERE user_id = :receiver) OR
             EXISTS (SELECT 1 FROM supervisor WHERE user_id = :receiver)),
            TRUE, FALSE)
            """,
            nativeQuery = true)
    boolean existsByUserReference(@Param("sender") String sender, @Param("receiver") String receiver);

    Page<Notification> findAllByReceiver(String receiver, Pageable pageable);


}
