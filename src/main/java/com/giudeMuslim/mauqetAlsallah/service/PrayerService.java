package com.giudeMuslim.mauqetAlsallah.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

import java.util.*;



@Service
public class PrayerService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> getPrayerTimes(double lat, double lng, String timezone) {
        String url = String.format(
                "https://api.aladhan.com/v1/timings?latitude=%f&longitude=%f&method=auto",
                lat, lng
        );

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JsonNode root = mapper.readTree(response.body().string());
            JsonNode timings = root.path("data").path("timings");
            JsonNode dateInfo = root.path("data").path("date");

            Map<String, String> result = new LinkedHashMap<>();
            result.put("fajr", timings.get("Fajr").asText());
            result.put("sunrise", timings.get("Sunrise").asText());
            result.put("dhuhr", timings.get("Dhuhr").asText());
            result.put("asr", timings.get("Asr").asText());
            result.put("maghrib", timings.get("Maghrib").asText());
            result.put("isha", timings.get("Isha").asText());

            result.put("serverUtc", Instant.now().toString());

            boolean isRamadan = dateInfo.path("hijri").path("month").path("number").asInt() == 9;
            result.put("isRamadan", String.valueOf(isRamadan));

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
