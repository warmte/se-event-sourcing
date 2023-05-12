package me.warmte.eventsourcing.repository;

import me.warmte.eventsourcing.entity.CheckpointEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointEventRepository extends JpaRepository<CheckpointEvent, Long> {
}
