package com.giudeMuslim.mauqetAlsallah.service;


import com.batoulapps.adhan.*;
import com.batoulapps.adhan.data.DateComponents;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PrayerService {


    public Map<String, String> getPrayerTimesMap(double lat, double lng, String timezone) {
        Coordinates coordinates = new Coordinates(lat, lng);
        CalculationParameters params = CalculationMethod.EGYPTIAN.getParameters();

        ZoneId zoneId = ZoneId.of(timezone);
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        DateComponents dateComponents = new DateComponents(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        PrayerTimes times = new PrayerTimes(coordinates, dateComponents, params);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(zoneId));

        Map<String, String> response = new LinkedHashMap<>();
        response.put("fajr", sdf.format(times.fajr));
        response.put("sunrise", sdf.format(times.sunrise));
        response.put("dhuhr", sdf.format(times.dhuhr));
        response.put("asr", sdf.format(times.asr));
        response.put("maghrib", sdf.format(times.maghrib));
        response.put("isha", sdf.format(times.isha));

        return response;
    }
}