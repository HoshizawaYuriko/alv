package com.example.alv.domain.statistics;

import com.example.alv.domain.listentry.Listentry;
import com.example.alv.domain.listentry.Status;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class StatusStatisticsService {
    // Get total amount of listentries by status
    public Map<Status, Long> countByStatus(List<Listentry> listentries) {
        Map<Status, Long> counts = new EnumMap<>(Status.class);

        // Initialize all statuses with zero
        for (Status status : Status.values()) {
            counts.put(status, 0L);
        }

        // Count listentries that match the status
        for (Listentry entry : listentries) {
            counts.merge(entry.getStatus(), 1L, Long::sum);
        }

        return counts;
    }
}
