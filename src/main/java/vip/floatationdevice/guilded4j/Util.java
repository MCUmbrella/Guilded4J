/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

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
}
