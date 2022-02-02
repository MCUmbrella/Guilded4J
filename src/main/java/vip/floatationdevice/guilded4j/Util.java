/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

public class Util
{
    public static void checkNullArgument(Object... objects)
    {
        for(Object object : objects)
            if (object==null) throw new IllegalArgumentException("Essential argument(s) shouldn't be null");
    }
}
