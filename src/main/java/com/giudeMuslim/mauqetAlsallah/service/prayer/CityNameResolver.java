package com.giudeMuslim.mauqetAlsallah.service.prayer;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class CityNameResolver {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL =
            "https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s";


    @Cacheable(value = "cityNames",
            key = "#lat + ',' + #lng + '-' + T(java.time.LocalDate).now().toString()")
    public String resolve(double lat, double lng) {
        try {
            double roundedLat = Math.round(lat * 1000.0) / 1000.0;
            double roundedLng = Math.round(lng * 1000.0) / 1000.0;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 Chrome/120.0.0.0 Safari/537.36");
            headers.set("Accept-Language", "ar");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    String.format(API_URL, roundedLat, roundedLng),
                    HttpMethod.GET, entity, Map.class
            );

            if (response.getBody() != null && response.getBody().containsKey("address")) {
                Map<String, Object> address = (Map<String, Object>) response.getBody().get("address");
                return String.valueOf(address.getOrDefault("suburb",
                        address.getOrDefault("city",
                                address.getOrDefault("town", "موقعك الحالي"))));
            }
        } catch (Exception e) {
            System.err.println("Cache Miss/Error: " + e.getMessage());
        }
        return "موقعك الحالي";
    }
    }
