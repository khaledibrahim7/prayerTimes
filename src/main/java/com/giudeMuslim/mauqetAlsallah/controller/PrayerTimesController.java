package com.giudeMuslim.mauqetAlsallah.controller;

import com.giudeMuslim.mauqetAlsallah.service.PrayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prayerTimes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PrayerTimesController {
    private final PrayerService prayerService;

    @GetMapping
    public ResponseEntity<Map<String, String>> getPrayerTimes(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam String timezone) {
        return ResponseEntity.ok(prayerService.getPrayerTimesMap(lat, lng, timezone));
    }

    @GetMapping(name = "/quran", produces = "text/plain;charset=UTF-8")
    public String quran(){
         return "هل قرات وردك من القرأن اليوم ؟";
        }

}
