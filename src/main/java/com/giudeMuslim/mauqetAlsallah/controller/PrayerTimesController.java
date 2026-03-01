package com.giudeMuslim.mauqetAlsallah.controller;

import com.giudeMuslim.mauqetAlsallah.service.PrayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/prayerTimes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PrayerTimesController {
    private final PrayerService prayerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTimes(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "UTC") String timezone) {

        Map<String, String> prayerTimes = prayerService.getPrayerTimes(lat, lng, timezone);
        Map<String, Object> response = new LinkedHashMap<>();
        response.putAll(prayerTimes);
        response.put("serverUtc", System.currentTimeMillis());
        response.put("isRamadan", Boolean.parseBoolean(prayerTimes.get("isRamadan")));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/quran", produces = "text/plain;charset=UTF-8")
    public String quran(){
         return "هل قرأتَ وِردَكَ من القرآنِ اليوم؟";
        }

}
