package com.giudeMuslim.mauqetAlsallah.service;


import com.batoulapps.adhan.*;
import com.batoulapps.adhan.data.DateComponents;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.batoulapps.adhan.CalculationMethod;
import org.springframework.web.client.RestTemplate;

@Service
public class PrayerService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final Map<String, String> countryCache = new ConcurrentHashMap<>();

    private String getCountryCode(double lat, double lng) {

        String key = String.format("%.2f,%.2f", lat, lng);

        if (countryCache.containsKey(key)) {
            return countryCache.get(key);
        }

        try {

            String url = "https://nominatim.openstreetmap.org/reverse?format=json"
                    + "&lat=" + lat
                    + "&lon=" + lng
                    + "&zoom=3&addressdetails=1";

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "SmartPrayerApp/1.0 (contact@smartprayer.app)");

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

        CalculationParameters params;

        switch (countryCode) {

            case "SA":
                params = CalculationMethod.UMM_AL_QURA.getParameters();
                params.madhab = Madhab.HANAFI;
                break;

            case "EG":
                params = CalculationMethod.EGYPTIAN.getParameters();
                params.madhab = Madhab.SHAFI;
                break;

            case "PK":
            case "IN":
            case "BD":
                params = CalculationMethod.KARACHI.getParameters();
                params.madhab = Madhab.HANAFI;
                break;

            case "US":
            case "CA":
                params = CalculationMethod.NORTH_AMERICA.getParameters();
                params.madhab = Madhab.SHAFI;
                break;

            case "GB":
            case "FR":
            case "DE":
            case "ES":
            case "IT":
                params = CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
                params.madhab = Madhab.SHAFI;
                break;

            default:
                params = CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
                params.madhab = Madhab.SHAFI;
        }

        params.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE;

        return params;
    }

    public Map<String, String> getPrayerTimesMap(double lat, double lng, String timezone) {

        String countryCode = getCountryCode(lat, lng);

        CalculationParameters params = getParamsByCountry(countryCode);

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

        PrayerTimes prayerTimes =
                new PrayerTimes(coordinates, dateComponents, params);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm");

        Map<String, String> response = new LinkedHashMap<>();

        response.put("fajr", prayerTimes.fajr.toInstant().atZone(zoneId).format(formatter));
        response.put("sunrise", prayerTimes.sunrise.toInstant().atZone(zoneId).format(formatter));
        response.put("dhuhr", prayerTimes.dhuhr.toInstant().atZone(zoneId).format(formatter));
        response.put("asr", prayerTimes.asr.toInstant().atZone(zoneId).format(formatter));
        response.put("maghrib", prayerTimes.maghrib.toInstant().atZone(zoneId).format(formatter));
        response.put("isha", prayerTimes.isha.toInstant().atZone(zoneId).format(formatter));

        response.put("serverUtc", Instant.now().toString());

        return response;
    }
}