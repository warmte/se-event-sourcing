package me.warmte.eventsourcing.service;

import me.warmte.eventsourcing.entity.CheckpointEvent;
import me.warmte.eventsourcing.repository.CheckpointEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointEventRepository checkpointEventRepository;

    @Autowired
    private ManagerService managerService;

    private long diffInOut(long subscriptionId) {
        List<CheckpointEvent> list1 = checkpointEventRepository.findAllBySubscriptionId(subscriptionId);
        long cnt = 0;
        for (CheckpointEvent elem : list1) {
            if (elem.getForwardDirection() == null) {
                throw new RuntimeException("something wrong");
            }
            if (elem.getForwardDirection()) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return cnt;
    }

    public String login(long subscriptionId) {
        LocalDateTime dateTime = managerService.getActualSubscription(subscriptionId);
        if (dateTime == null) {
            return null;
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            return null;
        }
        if (diffInOut(subscriptionId) != 0) {
            return null;
        }
        CheckpointEvent entity = new CheckpointEvent();
        entity.setSubscriptionId(subscriptionId);
        entity.setForwardDirection(true);
        entity.setTime(LocalDateTime.now());
        return checkpointEventRepository.save(entity).getTime().toString();
    }

    public String logout(long subscriptionId) {
        if (diffInOut(subscriptionId) != 1) {
            return null;
        }
        CheckpointEvent entity = new CheckpointEvent();
        entity.setSubscriptionId(subscriptionId);
        entity.setForwardDirection(false);
        entity.setTime(LocalDateTime.now());
        return checkpointEventRepository.save(entity).getTime().toString();
    }
}
