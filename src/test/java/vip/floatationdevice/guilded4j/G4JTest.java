package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Emote;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        Emote emo = Emote.fromJSON(new JSONObject("{\n" +
                "  \"id\": 90002551,\n" +
                "  \"name\": \"doge_gil\",\n" +
                "  \"url\": \"https://img.guildedcdn.com/asset/Emojis/Custom/doge_gil.webp\"\n" +
                "}"));
        System.out.println(new JSONObject(emo.toString()).toStringPretty());
    }
}
