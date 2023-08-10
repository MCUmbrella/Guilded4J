/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

public enum CalendarEventRepeatType
{
    ONCE,
    EVERY_DAY,
    EVERY_WEEK,
    EVERY_MONTH,
    CUSTOM;

    public static CalendarEventRepeatType fromString(String s)
    {
        switch(s)
        {
            case "once":
                return ONCE;
            case "everyDay":
                return EVERY_DAY;
            case "everyWeek":
                return EVERY_WEEK;
            case "everyMonth":
                return EVERY_MONTH;
            case "custom":
                return CUSTOM;
            default:
                throw new IllegalArgumentException("Unknown repeat type");
        }
    }

    @Override
    public String toString()
    {
        switch(this)
        {
            case ONCE:
                return "once";
            case EVERY_DAY:
                return "everyDay";
            case EVERY_WEEK:
                return "everyWeek";
            case EVERY_MONTH:
                return "everyMonth";
            case CUSTOM:
                return "custom";
            default:
                throw new RuntimeException("Should not happen");
        }
    }
}
