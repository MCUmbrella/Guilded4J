package vip.floatationdevice.guilded4j;

import cn.hutool.json.*;
import vip.floatationdevice.guilded4j.object.*;

public class G4JTest
{
    /**
     * Some temporary test code will go here.
     */
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession session = new G4JDebugger.G4JSession();
        session.restore();
        System.out.println(Util.isUUID(new Object[]{new NullPointerException(), System.console(), session}));
        System.out.println(Util.isUUID("00000000-0000-0000-0000-000000000000"));
        System.out.println(Util.isUUID("aAaAaAaA-AAAA-FFFF-3333-00000000000A"));
        System.out.println(Util.isUUID("00000000-0000-0000-0000-1145141919810"));
        System.out.println(Util.isUUID("24818abf-94db-4b28-816a-b327d106e83a"));
        System.out.println(Util.isUUID(""));
        Thread t = new Thread("00000000_0000_0000_0000_000000000000"){@Override public void run(){System.out.println("↑DONT BELIEVVE THAT IM REEALLY A UUID!!1!!!1!!!!");}};
        System.out.println(Util.isUUID(t.getName()));
        t.start();

        Object o = ServerChannel.fromString("{\n" +
                "  \"id\": \"00000000-0000-0000-0000-000000000000\",\n" +
                "  \"type\": \"chat\",\n" +
                "  \"name\": \"The Dank Cellar\",\n" +
                "  \"topic\": \"Dank memes ONLY\",\n" +
                "  \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "  \"createdBy\": \"Ann6LewA\",\n" +
                "  \"serverId\": \"wlVr3Ggl\",\n" +
                "  \"groupId\": \"ZVzBo83p\"\n" +
                "}");
        System.out.println(new JSONObject(o.toString()).toStringPretty());
    }
}
