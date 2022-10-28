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
        s = new G4JDebugger.G4JSession();
        s.restore();
        c = new G4JClient(s.savedToken).setVerbose(false).setAutoReconnect(true);
        //c.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 59909)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        //c.getWebSocketClient().setHeartbeatInterval(10000);
        //==============================================================
    }

    @Subscribe
    public void onForumTopicCommentCreatedEvent(ForumTopicCommentCreatedEvent e)
    {
        System.out.println(e.getForumTopicComment().toString());
    }

    @Subscribe
    public void onForumTopicCommentUpdatedEvent(ForumTopicCommentUpdatedEvent e)
    {
        System.out.println(e.getForumTopicComment().toString());
    }

    @Subscribe
    public void onForumTopicCommentDeletedEvent(ForumTopicCommentDeletedEvent e)
    {
        System.out.println(e.getForumTopicComment().toString());
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
