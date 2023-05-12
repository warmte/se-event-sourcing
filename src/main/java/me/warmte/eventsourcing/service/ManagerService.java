package me.warmte.eventsourcing.service;

import me.warmte.eventsourcing.entity.CreateSubscriptionEvent;
import me.warmte.eventsourcing.entity.ProlongSubscriptionEvent;
import me.warmte.eventsourcing.repository.CheckpointEventRepository;
import me.warmte.eventsourcing.repository.CreateSubscriptionEventRepository;
import me.warmte.eventsourcing.repository.ProlongSubscriptionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private CheckpointEventRepository checkPointEventRepository;

    @Autowired
    private CreateSubscriptionEventRepository createSubscriptionEventRepository;

    @Autowired
    private ProlongSubscriptionEventRepository prolongSubscriptionEventRepository;

    private LocalDateTime convert(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH,mm,dd,MM,yyyy");
        LocalDateTime result = null;
        try {
            result = LocalDateTime.parse(s, formatter);
        } catch (DateTimeParseException e) {
            // nothing
        }
        return result;
    }

    private List<LocalDateTime> getSubscriptionListInfo(long subscriptionId) {
        List<LocalDateTime> answer = new ArrayList<>();
        CreateSubscriptionEvent entity1 = createSubscriptionEventRepository.findById(subscriptionId).orElse(null);
        if (entity1 == null) {
            return null;
        }
        answer.add(entity1.getExpiryDateTime());
        List<ProlongSubscriptionEvent> list1 = prolongSubscriptionEventRepository.findAllBySubscriptionId(subscriptionId);
        for (ProlongSubscriptionEvent elem : list1) {
            answer.add(elem.getExpiryDateTime());
        }
        return answer;
    }

    public String getSubscriptionInfo(long subscriptionId) {
        List<LocalDateTime> list1 = getSubscriptionListInfo(subscriptionId);
        if (list1 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (LocalDateTime elem : list1) {
            sb.append(elem.toString()).append(", ");
        }
        return sb.toString();
    }

    public String createSubscription(String expiry) {
        if (convert(expiry) == null) {
            return null;
        }
        CreateSubscriptionEvent entity = new CreateSubscriptionEvent();
        entity.setExpiryDateTime(convert(expiry));
        return String.valueOf(createSubscriptionEventRepository.save(entity).getId());
    }

    public String prolongSubscription(Long subscriptionId, String expiry) {
        List<LocalDateTime> list1 = getSubscriptionListInfo(subscriptionId);
        if (list1 == null) {
            return null;
        }
        if (list1.size() == 0) {
            return null;
        }
        LocalDateTime dateTime = list1.get(list1.size() - 1);
        if (dateTime.isAfter(convert(expiry))) {
            return null;
        }
        ProlongSubscriptionEvent entity2 = new ProlongSubscriptionEvent();
        entity2.setSubscriptionId(subscriptionId);
        entity2.setExpiryDateTime(convert(expiry));
        return prolongSubscriptionEventRepository.save(entity2).getExpiryDateTime().toString();
    }
}
