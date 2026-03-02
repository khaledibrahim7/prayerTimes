package com.giudeMuslim.mauqetAlsallah.controller;

import com.giudeMuslim.mauqetAlsallah.dto.PrayerResponse;
import com.giudeMuslim.mauqetAlsallah.service.prayer.PrayerService;
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
    public ResponseEntity<PrayerResponse> getPrayers(@RequestParam double lat,
                                                     @RequestParam double lng,
                                                     @RequestParam String timezone) {
        PrayerResponse response = prayerService.getUniversalPrayerTimes(lat, lng, timezone);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/quran", produces = "text/plain;charset=UTF-8")
    public String quran(){
         return "هل قرأتَ وِردَكَ من القرآنِ اليوم؟";
        }

}
