package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.exception.GuildedException;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args)
    {
        GuildedException e = GuildedException.fromString("{\n" +
                "  \"code\": \"ForbiddenError\",\n" +
                "  \"message\": \"You do not have the correct permissions to perform this request\",\n" +
                "  \"meta\": {\n" +
                "    \"missingPermissions\": [\n" +
                "      \"Kick / Ban members\",\n" +
                "      \"AAAAA\",\n" +
                "      \"B\"\n" +
                "    ],\n" +
                "    \"test\": 123432,\n" +
                "    \"test2\": {\n" +
                "      \"test2.1\": 2.1\n" +
                "    }\n" +
                "  }\n" +
                "}");
        System.out.println(e);
        System.out.println(e.getMeta());
        System.out.println((int) e.getMeta().get("test"));
        System.out.println(new JSONObject(e.getMeta().get("test2")).getFloat("test2.1"));
        JSONArray missingPermissions = (JSONArray) e.getMeta().get("missingPermissions");
        for(Object s : missingPermissions)
            System.out.println(s);
        GuildedException e2 = GuildedException.fromString("{\n" +
                "  \"code\": \"ForbiddenError\",\n" +
                "  \"message\": \"You do not have the correct permissions to perform this request\",\n" +
                "}");
        System.out.println(e2.getMeta());
    }
}
