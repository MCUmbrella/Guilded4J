package vip.floatationdevice.guilded4j;

public class Util
{
    public static void checkNullArgument(Object... objects)
    {
        for(Object object : objects)
            if (object==null) throw new IllegalArgumentException("Essential argument(s) shouldn't be null");
    }
}
