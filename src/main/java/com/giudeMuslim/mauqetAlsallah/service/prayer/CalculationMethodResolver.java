package com.giudeMuslim.mauqetAlsallah.service.prayer;

import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.Coordinates;
import org.springframework.stereotype.Component;

@Component
public class CalculationMethodResolver {
    public CalculationMethod resolve(Coordinates c) {

        if (within(c, 22.0, 31.6, 25.0, 37.0))
            return CalculationMethod.EGYPTIAN;

        if (within(c, 12.0, 32.5, 34.0, 60.0))
            return CalculationMethod.UMM_AL_QURA;

        if (within(c, 5.0, 37.0, 60.0, 95.0))
            return CalculationMethod.KARACHI;

        if (within(c, 24.0, 71.0, -168.0, -52.0))
            return CalculationMethod.NORTH_AMERICA;

        return CalculationMethod.MUSLIM_WORLD_LEAGUE;
    }

    private boolean within(Coordinates c,
                           double minLat, double maxLat,
                           double minLng, double maxLng) {

        return c.latitude >= minLat && c.latitude <= maxLat &&
                c.longitude >= minLng && c.longitude <= maxLng;
    }
}