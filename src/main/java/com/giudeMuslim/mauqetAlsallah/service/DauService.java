package com.giudeMuslim.mauqetAlsallah.service;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class DauService {

    public String getRandomDua() {
        String[] duas = {
                "اللهم إنك عفو تحب العفو فاعف عنا",
                "ربنا آتنا في الدنيا حسنة وفي الآخرة حسنة وقنا عذاب النار",
                "اللهم إني أسألك الهدى والتقى والعفاف والغنى",
                "يا مقلب القلوب ثبت قلبي على دينك",
                "اللهم اغفر لي وارحمني واهدني وعافني وارزقني",
                "اللهم إني أعوذ بك من الهم والحزن، والعجز والكسل",
                "رب اشرح لي صدري ويسر لي أمري",
                "اللهم إني أعوذ بك من زوال نعمتك وتحول عافيتك"
        };
        return duas[new Random().nextInt(duas.length)];
    }
}
