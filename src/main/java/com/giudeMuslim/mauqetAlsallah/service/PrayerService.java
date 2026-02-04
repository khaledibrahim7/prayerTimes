package com.giudeMuslim.mauqetAlsallah.service;


import com.batoulapps.adhan.*;
import com.batoulapps.adhan.data.DateComponents;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PrayerService {
    public Map<String, String> getPrayerTimesMap(double lat, double lng) {
        Coordinates coordinates = new Coordinates(lat, lng);
        CalculationParameters params = CalculationMethod.EGYPTIAN.getParameters();
        DateComponents dateComponents = DateComponents.from(new Date());
        PrayerTimes times = new PrayerTimes(coordinates, dateComponents, params);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

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
