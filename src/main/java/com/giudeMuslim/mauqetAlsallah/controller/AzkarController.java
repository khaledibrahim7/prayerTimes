package com.giudeMuslim.mauqetAlsallah.controller;

import com.giudeMuslim.mauqetAlsallah.service.AzkarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/azkar")
@RestController
@RequiredArgsConstructor
public class AzkarController {
    private final AzkarService AzkarService;


    @GetMapping("/salyAlaELnaby")
    public String salyAlaELnaby() {
        return AzkarService.salyAlaELnaby();
    }

    @GetMapping
    public String getRandomZikr() {
        return AzkarService.RandomZikr();
    }
}
