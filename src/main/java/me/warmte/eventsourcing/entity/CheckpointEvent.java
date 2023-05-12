package me.warmte.eventsourcing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CheckpointEvent {
    @Id
    @GeneratedValue
    private Long id;

    private Long subscriptionId;

    private Boolean isForwardDirection;

    private LocalDateTime time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Boolean getForwardDirection() {
        return isForwardDirection;
    }

    public void setForwardDirection(Boolean forwardDirection) {
        isForwardDirection = forwardDirection;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
