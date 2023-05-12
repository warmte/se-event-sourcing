package me.warmte.eventsourcing.service_reporting;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CheckpointEventDTO {
    private final LocalDateTime time;

    private final LocalDate date;

    private final Long subscriptionId;

    private final Boolean isForwardDirection;

    public CheckpointEventDTO(LocalDateTime time, Long subscriptionId, Boolean isForwardDirection) {
        this.time = time;
        this.date = time.toLocalDate();
        this.subscriptionId = subscriptionId;
        this.isForwardDirection = isForwardDirection;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public Boolean getForwardDirection() {
        return isForwardDirection;
    }
}
