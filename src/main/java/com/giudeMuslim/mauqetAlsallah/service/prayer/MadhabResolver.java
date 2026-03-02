package com.giudeMuslim.mauqetAlsallah.service.prayer;

import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.Madhab;
import org.springframework.stereotype.Component;

@Component
public class MadhabResolver {


    public Madhab resolve(Coordinates c) {

        if (within(c, 23.0, 42.0, 60.0, 95.0) ||
                within(c, 36.0, 42.0, 26.0, 45.0)) {

            return Madhab.HANAFI;
        }

        return Madhab.SHAFI;
    }

    private boolean within(Coordinates c,
                           double minLat, double maxLat,
                           double minLng, double maxLng) {

        return c.latitude >= minLat && c.latitude <= maxLat &&
                c.longitude >= minLng && c.longitude <= maxLng;
    }
}
