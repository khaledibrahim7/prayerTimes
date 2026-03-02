package com.giudeMuslim.mauqetAlsallah.service.prayer;

import com.batoulapps.adhan.*;
import com.batoulapps.adhan.data.DateComponents;
import com.giudeMuslim.mauqetAlsallah.dto.PrayerResponse;
import com.giudeMuslim.mauqetAlsallah.mapper.PrayerTimeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class PrayerService {

    private final LocationSnapper locationSnapper;
    private final CalculationMethodResolver methodResolver;
    private final MadhabResolver madhabResolver;
    private final PrayerTimeMapper prayerTimeMapper;
    private final CityNameResolver cityNameResolver;

    public PrayerResponse getUniversalPrayerTimes(double lat, double lng, String timezoneId) {

        ZoneId zoneId = ZoneId.of(timezoneId);
        ZonedDateTime now = ZonedDateTime.now(zoneId);

        Coordinates coordinates = locationSnapper.snap(lat, lng);

        DateComponents date = new DateComponents(
                now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth()
        );

        CalculationMethod method = methodResolver.resolve(coordinates);
        CalculationParameters params = method.getParameters();

        params.madhab = madhabResolver.resolve(coordinates);
        params.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE;
        String cityName = cityNameResolver.resolve(lat, lng);
        PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

        return new PrayerResponse(
                method.name(),
                cityName,
                timezoneId,
                prayerTimeMapper.map(prayerTimes, zoneId)
        );
    }
}