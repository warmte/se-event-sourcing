package me.warmte.eventsourcing.service_reporting;

import me.warmte.eventsourcing.util.Pair;
import me.warmte.eventsourcing.repository.CheckpointEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportingService {
    @Autowired
    private CheckpointEventRepository checkpointEventRepository;

    private LocalDateTime lastUpdateTime = LocalDateTime.MIN;
    private final HashMap<LocalDate, Integer> dayStatistic = new HashMap<>();
    private final HashMap<Long, Pair<Long, Long>> averageStatistic = new HashMap<>();

    private LocalDate convert(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,yyyy");
        try {
            LocalDateTime result = LocalDateTime.parse(s, formatter);
            return result.toLocalDate();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public void update() {
        LocalDateTime currTime = LocalDateTime.now();
        List<CheckpointEventDTO> addAllList = checkpointEventRepository
                .findAll()
                .stream()
                .filter(obj -> obj.getTime().isAfter(lastUpdateTime) && obj.getTime().isBefore(currTime))
                .map(obj -> new CheckpointEventDTO(obj.getTime(), obj.getSubscriptionId(), obj.getForwardDirection()))
                .toList();
        addAllList.stream()
                .collect(Collectors.groupingBy(CheckpointEventDTO::getDate))
                .forEach((key, value) -> {
                    if (dayStatistic.containsKey(key)) {
                        Integer old = dayStatistic.get(key);
                        dayStatistic.put(key, old + value.size());
                    } else {
                        dayStatistic.put(key, value.size());
                    }
                });
        addAllList.stream()
                .collect(Collectors.groupingBy(CheckpointEventDTO::getSubscriptionId))
                .forEach((key, value) -> {
                    List<CheckpointEventDTO> sortTmpList = value.stream()
                            .sorted((x, y) -> {
                                if (x.getTime().isBefore(y.getTime())) {
                                    return -1;
                                } else {
                                    if (x.getTime().isEqual(y.getTime())) {
                                        return 0;
                                    } else {
                                        return 1;
                                    }
                                }
                            }).toList();
                    long totalMinutes = 0;
                    for (int i = 0; i < sortTmpList.size(); i += 2) {
                        totalMinutes += Duration.between(sortTmpList.get(i).getTime(), sortTmpList.get(i + 1).getTime()).toMinutes();
                    }
                    long totalVis = sortTmpList.size() / 2;
                    Pair<Long, Long> newP = new Pair<>(totalMinutes, totalVis);
                    if (averageStatistic.containsKey(key)) {
                        Pair<Long, Long> oldP = averageStatistic.get(key);
                        averageStatistic.put(key, new Pair<>(oldP.first + newP.first, oldP.second + newP.second));
                    } else {
                        averageStatistic.put(key, newP);
                    }
                });
        lastUpdateTime = currTime;
    }

    public Integer getDayStatistic(String date) {
        if (convert(date) == null) {
            return null;
        }
        return dayStatistic.get(convert(date));
    }

    public Pair<Long, Long> getAverageStatistic(long subscriptionId) {
        return averageStatistic.get(subscriptionId);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void doSomethingAfterStartup() {
        System.out.println("hello world, I have just started up");
        update();
    }
}
