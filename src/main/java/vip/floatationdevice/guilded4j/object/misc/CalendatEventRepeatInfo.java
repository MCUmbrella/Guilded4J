/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import vip.floatationdevice.guilded4j.enums.CalendarEventRepeatType;
import vip.floatationdevice.guilded4j.enums.Weekday;

public class CalendatEventRepeatInfo //TODO
{
    public enum Interval
    {
        DAY,
        WEEK,
        MONTH,
        YEAR;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }

    private CalendarEventRepeatType type;
    private Integer everyCount;
    private Interval interval;
    private Integer endsAfterOccurrences;
    private String endDate;
    private Weekday on;
}
