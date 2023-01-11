package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.GuildedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketClosedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketWelcomeEvent;
import vip.floatationdevice.guilded4j.event.UnknownGuildedEvent;

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
        //c.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 59909)));
        c.registerEventListener(new G4JTest()).connectWebSocket(true, null);
        c.getForumManager().addReaction("6284cada-9d78-4941-803a-3bc38e3de9aa", 2076691721, 90001164);
        c.getForumManager().addReaction("6284cada-9d78-4941-803a-3bc38e3de9aa", 2076691721, 2124074763, 90001164);
        System.out.println("getForumManager().addReaction() OK");
        sc.nextLine();
        c.getForumManager().removeReaction("6284cada-9d78-4941-803a-3bc38e3de9aa", 2076691721, 90001164);
        c.getForumManager().removeReaction("6284cada-9d78-4941-803a-3bc38e3de9aa", 2076691721, 2124074763, 90001164);
        System.out.println("getForumManager().removeReaction() OK");
        sc.nextLine();
        c.getChatMessageManager().addReaction("e4b1cd5a-721a-47dc-8e23-c04ecf31c206", "229dd339-16ba-43a2-9957-726af1e3bfdc", 90001164);
        System.out.println("getChatMessageManager().addReaction() OK");
        sc.nextLine();
        c.getChatMessageManager().removeReaction("e4b1cd5a-721a-47dc-8e23-c04ecf31c206", "229dd339-16ba-43a2-9957-726af1e3bfdc", 90001164);
        System.out.println("getChatMessageManager().removeReaction() OK");
        //==============================================================
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
