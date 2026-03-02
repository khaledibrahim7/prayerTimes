package com.giudeMuslim.mauqetAlsallah.service.prayer;

import com.batoulapps.adhan.Coordinates;
import org.springframework.stereotype.Component;

@Component
public class LocationSnapper {


    public Coordinates snap(double lat, double lng) {

        if (within(lat, lng, 29.5, 30.5, 30.5, 32.5))
            return new Coordinates(30.0444, 31.2357);

        if (within(lat, lng, 31.0, 31.3, 29.7, 30.1))
            return new Coordinates(31.2001, 29.9187);

        double snappedLat = Math.round(lat * 10.0) / 10.0;
        double snappedLng = Math.round(lng * 10.0) / 10.0;

        return new Coordinates(snappedLat, snappedLng);
    }

    private boolean within(double lat, double lng,
                           double minLat, double maxLat,
                           double minLng, double maxLng) {

        return lat >= minLat && lat <= maxLat &&
                lng >= minLng && lng <= maxLng;
    }
}
