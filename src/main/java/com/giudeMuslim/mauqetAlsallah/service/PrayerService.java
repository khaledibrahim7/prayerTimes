package com.giudeMuslim.mauqetAlsallah.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Service
public class PrayerService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public Map<String, String> getPrayerTimes(double lat, double lng, int method) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String url = String.format(
                "https://api.aladhan.com/v1/timings/%s?latitude=%f&longitude=%f&method=%d",
                date, lat, lng, method
        );

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) return Collections.emptyMap();

            JsonNode root = mapper.readTree(response.body().string());
            JsonNode data = root.path("data");
            JsonNode timings = data.path("timings");
            JsonNode dateInfo = data.path("date");

            Map<String, String> result = new LinkedHashMap<>();
            result.put("fajr", timings.path("Fajr").asText());
            result.put("sunrise", timings.path("Sunrise").asText());
            result.put("dhuhr", timings.path("Dhuhr").asText());
            result.put("asr", timings.path("Asr").asText());
            result.put("maghrib", timings.path("Maghrib").asText());
            result.put("isha", timings.path("Isha").asText());
            result.put("serverUtc", Instant.now().toString());

            boolean isRamadan = dateInfo.path("hijri").path("month").path("number").asInt() == 9;
            result.put("isRamadan", String.valueOf(isRamadan));

            return result;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }
}