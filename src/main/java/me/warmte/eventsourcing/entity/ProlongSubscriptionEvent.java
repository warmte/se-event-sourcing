package me.warmte.eventsourcing.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProlongSubscriptionEvent {
    @Id
    @GeneratedValue
    private Long id;

    private Long subscriptionId;

    private LocalDateTime expiryDateTime;

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

    public LocalDateTime getExpiryDateTime() {
        return expiryDateTime;
    }

    public void setExpiryDateTime(LocalDateTime expiryDateTime) {
        this.expiryDateTime = expiryDateTime;
    }
}
