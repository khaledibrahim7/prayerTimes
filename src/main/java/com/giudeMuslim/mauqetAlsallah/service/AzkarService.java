package com.giudeMuslim.mauqetAlsallah.service;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class AzkarService {


    public String RandomZikr() {
        String[] azkar = {
                "استغفر الله",
                "سبحان الله",
                "الحمد لله",
                "الله أكبر",
                "سبحان الله وبحمده",
                "لا حول ولا قوة إلا بالله",
                "سبحان الله العظيم",
                "لا إله إلا الله"};

        return azkar[new Random().nextInt(azkar.length)];
    }

    public String salyAlaELnaby() {
        return "صلي ع النبي";
    }
}
