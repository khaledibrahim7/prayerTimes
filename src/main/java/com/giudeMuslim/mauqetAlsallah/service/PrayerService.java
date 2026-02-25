package com.giudeMuslim.mauqetAlsallah.service;


import com.batoulapps.adhan.*;
import com.batoulapps.adhan.data.DateComponents;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.batoulapps.adhan.CalculationMethod;
import org.springframework.web.client.RestTemplate;

@Service
public class PrayerService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, String> countryCache = new HashMap<>();


    private String getCountryCode(double lat, double lng) {

        String key = lat + "," + lng;

        if (countryCache.containsKey(key)) {
            return countryCache.get(key);
        }

        try {
            String url = "https://nominatim.openstreetmap.org/reverse?format=json"
                    + "&lat=" + lat
                    + "&lon=" + lng
                    + "&zoom=3&addressdetails=1";

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "SmartPrayerApp");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() == null) return "DEFAULT";

            Map body = response.getBody();
            Map address = (Map) body.get("address");

            if (address == null || address.get("country_code") == null)
                return "DEFAULT";

            String code = address.get("country_code").toString().toUpperCase();

            countryCache.put(key, code);

            return code;

        } catch (Exception e) {
            return "DEFAULT";
        }
    }


    private CalculationParameters getParamsByCountry(String countryCode) {

        switch (countryCode) {

            case "SA":
                return CalculationMethod.UMM_AL_QURA.getParameters();

            case "EG":
                return CalculationMethod.EGYPTIAN.getParameters();

            case "PK":
            case "IN":
            case "BD":
                return CalculationMethod.KARACHI.getParameters();

            case "US":
            case "CA":
                return CalculationMethod.NORTH_AMERICA.getParameters();

            case "GB":
            case "FR":
            case "DE":
            case "ES":
            case "IT":
                return CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();

            default:
                return CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
        }
    }


    public Map<String, String> getPrayerTimesMap(double lat, double lng, String timezone) {

        String countryCode = getCountryCode(lat, lng);

        CalculationParameters params = getParamsByCountry(countryCode);

        params.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE;
        params.madhab = Madhab.SHAFI;

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(timezone);
        } catch (Exception e) {
            zoneId = ZoneId.of("UTC");
        }

        ZonedDateTime now = ZonedDateTime.now(zoneId);

        DateComponents dateComponents =
                new DateComponents(
                        now.getYear(),
                        now.getMonthValue(),
                        now.getDayOfMonth()
                );

        Coordinates coordinates = new Coordinates(lat, lng);

        PrayerTimes times =
                new PrayerTimes(coordinates, dateComponents, params);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm");

        Map<String, String> response = new LinkedHashMap<>();

        response.put("fajr", times.fajr.toInstant().atZone(zoneId).format(formatter));
        response.put("sunrise", times.sunrise.toInstant().atZone(zoneId).format(formatter));
        response.put("dhuhr", times.dhuhr.toInstant().atZone(zoneId).format(formatter));
        response.put("asr", times.asr.toInstant().atZone(zoneId).format(formatter));
        response.put("maghrib", times.maghrib.toInstant().atZone(zoneId).format(formatter));
        response.put("isha", times.isha.toInstant().atZone(zoneId).format(formatter));

        return response;

    }
}