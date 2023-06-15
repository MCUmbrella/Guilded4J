/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.enums;

public enum Weekday
{
    MON,
    TUE,
    WED,
    THU,
    FRI,
    SAT,
    SUN;

    public static Weekday fromString(String s)
    {
        switch (s.toLowerCase())
        {
            case "monday":
                return MON;
            case "tuesday":
                return TUE;
            case "wednesday":
                return WED;
            case "thursday":
                return THU;
            case "friday":
                return FRI;
            case "saturday":
                return SAT;
            case "sunday":
                return SUN;
            default:
                throw new IllegalArgumentException("Invalid weekday");
        }
    }

    @Override
    public String toString()
    {
        return name().toLowerCase();
    }
}
