package com.giudeMuslim.mauqetAlsallah.controller;

import com.batoulapps.adhan.PrayerTimes;
import com.giudeMuslim.mauqetAlsallah.service.PrayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/prayerTimes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PrayerTimesController {
    private final PrayerService prayerService;

    @GetMapping
    public ResponseEntity<Map<String, String>> getPrayerTimes(@RequestParam double lat, @RequestParam double lng) {
        return ResponseEntity.ok(prayerService.getPrayerTimesMap(lat, lng));
    }
}
