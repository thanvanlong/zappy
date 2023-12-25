package com.longtv.zappy.network.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class UsageTime {
    private LocalTime currentTime;
    private Map<String, Long> usageTime;

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalTime currentTime) {
        this.currentTime = currentTime;
    }

    public Map<String, Long> getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(Map<String, Long> usageTime) {
        this.usageTime = usageTime;
    }
}
