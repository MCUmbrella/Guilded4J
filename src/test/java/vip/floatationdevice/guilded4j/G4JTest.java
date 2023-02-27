package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.object.CalendarEventComment;

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
        s = new G4JDebugger.G4JSession();
        s.restore();
        c = new G4JClient(s.savedToken).setVerbose(true).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 9910)));
        //c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        //==============================================================
        System.out.println(
                new JSONObject(
                        CalendarEventComment.fromJSON(new JSONObject("{\n" +
                                "  \"id\": 1234567890,\n" +
                                "  \"content\": \"I will be there!!\",\n" +
                                "  \"channelId\": \"00000000-0000-0000-0000-000000000000\",\n" +
                                "  \"createdAt\": \"2022-06-15T20:15:00.706Z\",\n" +
                                "  \"createdBy\": \"Ann6LewA\",\n" +
                                "  \"calendarEventId\": 987654321\n" +
                                "}")
                        ).toString()
                ).toStringPretty()
        );
        System.exit(0);
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
