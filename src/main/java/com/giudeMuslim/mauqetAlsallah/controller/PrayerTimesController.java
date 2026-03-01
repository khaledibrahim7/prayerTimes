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
    public ResponseEntity<Map<String, String>> getPrayers(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "3") int method) {

        Map<String, String> times = prayerService.getPrayerTimes(lat, lng, method);

        if (times.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(times);
    }

    @GetMapping(value = "/quran", produces = "text/plain;charset=UTF-8")
    public String quran(){
         return "هل قرأتَ وِردَكَ من القرآنِ اليوم؟";
        }

}
