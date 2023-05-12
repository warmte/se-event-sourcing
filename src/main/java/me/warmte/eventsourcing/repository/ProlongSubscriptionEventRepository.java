package me.warmte.eventsourcing.repository;

import me.warmte.eventsourcing.entity.ProlongSubscriptionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProlongSubscriptionEventRepository extends JpaRepository<ProlongSubscriptionEvent, Long> {
    List<ProlongSubscriptionEvent> findAllBySubscriptionId(long subscriptionId);
}
