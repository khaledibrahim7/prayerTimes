package com.giudeMuslim.mauqetAlsallah.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record PrayerResponse(String method,
                             String cityName,
                             String timezone,
                             LocalDateTime dateTime,
                             Map<String, String> times) {
}
