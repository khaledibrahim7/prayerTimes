package com.giudeMuslim.mauqetAlsallah.dto;

import java.util.Map;

public record PrayerResponse(String method,
                             String cityName,
                             String timezone,
                             Map<String, String> times) {
}
