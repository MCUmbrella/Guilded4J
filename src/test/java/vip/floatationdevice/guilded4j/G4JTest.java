package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.Server;
import vip.floatationdevice.guilded4j.rest.ServerManager;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args)
    {
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        System.out.println(new JSONObject(new ServerManager(s.savedToken).getServer("Mldy328E").toString()).toStringPretty());
    }
}
