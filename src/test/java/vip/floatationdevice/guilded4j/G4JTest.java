package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.CalendarEvent;

import java.util.Arrays;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        CalendarEvent ce = CalendarEvent.fromJSON(new JSONObject("{\n" +
                "    \"id\": 1,\n" +
                "    \"serverId\": \"wlVr3Ggl\",\n" +
                "    \"channelId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "    \"name\": \"Surprise LAN party for my wife \uD83E\uDD2B\",\n" +
                "    \"description\": \"**Don't say anything to her!** She's gonna love playing Call of Duty all night\",\n" +
                "    \"location\": \"My house!\",\n" +
                "    \"url\": \"https://www.surprisepartygame.com/\",\n" +
                "    \"color\": 16106496,\n" +
                "    \"startsAt\": \"2022-06-16T00:00:00.000Z\",\n" +
                "    \"duration\": 60,\n" +
                "    \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "    \"createdBy\": \"Ann6LewA\",\n" +
                "    \"cancellation\":{\"description\":\"awa\",\"createdBy\":\"1111111\"},\n" +
                "    \"mentions\":[\"awawawa\",\"aaaa\",\"w\"]\n" +
                "}"));
        System.out.println(new JSONObject(ce.toString()).toStringPretty());
        System.out.println(Arrays.toString(ce.getMentions()));
    }
}
