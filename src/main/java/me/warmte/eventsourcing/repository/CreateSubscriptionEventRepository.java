package me.warmte.eventsourcing.repository;

import me.warmte.eventsourcing.entity.CreateSubscriptionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreateSubscriptionEventRepository extends JpaRepository<CreateSubscriptionEvent, Long> {
}
