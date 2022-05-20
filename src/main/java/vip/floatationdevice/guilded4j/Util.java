/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import java.util.Calendar;
import java.util.TimeZone;

public class Util
{
    public static final String UUID_REGEX = "^[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}$";

    public static void checkNullArgument(Object... objects)
    {
        for(Object object : objects)
        {
            //System.out.println(object);
            if(object == null) throw new IllegalArgumentException("Essential argument(s) shouldn't be null");
        }
    }

    public static boolean isUUID(Object o){return o != null && o.toString().matches(UUID_REGEX);}

    /**
     * Converts ISO8601 timestamp to Calendar object.
     * @param s ISO8601 timestamp (something like "2021-06-15T20:15:01.706Z")
     * @return Calendar object.
     */
    public static Calendar iso8601ToCalendar(String s)
    {
        Calendar calendar = Calendar.getInstance();
        String[] parts = s.split("T"); // [2021-06-15, 20:15:01.706Z]
        String[] dateParts = parts[0].split("-"); // [2021, 06, 15]
        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1); // January is 0
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
        String[] timeParts = parts[1].split(":"); // [20, 15, 01.706Z]
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        String[] secondsParts = timeParts[2].split("\\."); // [01, 706Z]
        calendar.set(Calendar.SECOND, Integer.parseInt(secondsParts[0]));
        if(secondsParts.length > 1)
            calendar.set(Calendar.MILLISECOND, Integer.parseInt(secondsParts[1].substring(0, 3))); // drops 'Z'
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        return calendar;
    }

    /**
     * Converts Calendar object to ISO8601 timestamp.
     * @param c Calendar object
     * @return ISO8601 timestamp (something like "2021-06-15T20:15:01.706Z")
     */
    public static String calendarToIso8601(Calendar c)
    {
        return String.format("%d-%02d-%02dT%02d:%02d:%02d.%03dZ",
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND),
                c.get(Calendar.MILLISECOND));
    }
}
