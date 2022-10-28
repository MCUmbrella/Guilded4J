package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.misc.*;
import vip.floatationdevice.guilded4j.object.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Scanner;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    static G4JClient c;
    static G4JDebugger.G4JSession s;
    static String lastMessageId;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception
    {
        //==============================================================
        /*s = new G4JDebugger.G4JSession();
        s.restore();
        c = new G4JClient(s.savedToken).setVerbose(true).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 59909)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        c.getWebSocketClient().setHeartbeatInterval(10000);*/
        //==============================================================
        System.out.println(new JSONObject(ForumTopicComment.fromJSON(new JSONObject("{\n" +
                "  \"id\": 1234567890,\n" +
                "  \"content\": \"Great idea!\",\n" +
                "  \"createdAt\": \"2021-06-15T20:15:00.706Z\",\n" +
                "  \"forumTopicId\": 9876543210,\n" +
                "  \"createdBy\": \"Ann6LewA\",\n" +
                "  \"updatedAt\": \"2021-06-15T20:15:00.706Z\"" +
                "}")).toString()).toStringPretty());
    }

    @Subscribe
    public void onConnect(GuildedWebSocketWelcomeEvent e)
    {
        System.out.println("Connected");
    }

    @Subscribe
    public void onDisconnect(GuildedWebSocketClosedEvent e)
    {
        System.out.println("Disconnected");
    }

    @Subscribe
    public void onUnknownGuildedEvent(UnknownGuildedEvent e)
    {
        System.err.println("===== Unknown Guilded event =====\nRaw: " + e.getRawString() + "\nReason: " + e.getReason());
        if(e.getReason() != null) e.getReason().printStackTrace();
    }

    @Subscribe
    public void onGuildedEvent(GuildedEvent e)
    {
        lastMessageId = e.getEventID();
    }
}
