package com.giudeMuslim.mauqetAlsallah.mapper;

import com.batoulapps.adhan.PrayerTimes;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class PrayerTimeMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public Map<String, String> map(PrayerTimes times, ZoneId zoneId) {
        Map<String, Date> raw = new LinkedHashMap<>();
        raw.put("fajr", times.fajr);
        raw.put("sunrise", times.sunrise);
        raw.put("dhuhr", times.dhuhr);
        raw.put("asr", times.asr);
        raw.put("maghrib", times.maghrib);
        raw.put("isha", times.isha);

        return raw.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> format(e.getValue(), zoneId),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private String format(Date date, ZoneId zoneId) {
        if (date == null) return "N/A";
        return date.toInstant()
                .atZone(zoneId)
                .format(FORMATTER);
    }
}
