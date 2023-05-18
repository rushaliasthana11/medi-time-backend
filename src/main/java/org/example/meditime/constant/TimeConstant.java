package org.example.meditime.constant;

import org.springframework.stereotype.Component;

@Component
public final class TimeConstant {

    public static final Integer MORNING_MEDS_MIN_HOUR = 7;
    public static final Integer MORNING_MEDS_MAX_HOUR = 12;
    public static final Integer AFTERNOON_MEDS_MIN_HOUR = 13;
    public static final Integer AFTERNOON_MEDS_MAX_HOUR = 16;
    public static final Integer EVENING_MEDS_MIN_HOUR = 19;
    public static final Integer EVENING_MEDS_MAX_HOUR = 23;

}
