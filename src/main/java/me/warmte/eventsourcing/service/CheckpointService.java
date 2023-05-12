package me.warmte.eventsourcing.service;

import me.warmte.eventsourcing.repository.CheckpointEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckpointService {
    @Autowired
    private CheckpointEventRepository checkPointEventRepository;
}
