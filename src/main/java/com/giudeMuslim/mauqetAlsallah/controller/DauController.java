package com.giudeMuslim.mauqetAlsallah.controller;

import com.giudeMuslim.mauqetAlsallah.service.DauService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dua")
@RequiredArgsConstructor
public class DauController {

    private final DauService dauService;

    @RequestMapping
    public String getRandomDua() {
        return dauService.getRandomDua();
    }
}
