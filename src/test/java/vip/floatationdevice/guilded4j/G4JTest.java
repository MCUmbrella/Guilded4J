package vip.floatationdevice.guilded4j;

import cn.hutool.http.Method;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.rest.RestManager;

/**
 * Some temporary test code will go here.
 */
public class G4JTest
{
    public static void main(String[] args) throws Exception
    {
        //==============================================================
        G4JDebugger.G4JSession s = new G4JDebugger.G4JSession();
        s.restore();
        G4JWebSocketClient ws = new G4JWebSocketClient(s.savedToken);
        ws.eventBus.register(new G4JTest());
        ws.connect();
        new RestManager(s.savedToken){}.execute(Method.GET, "http://nwnsineubyuanhuiqx3b87ohiuynexybuabhbwuiuo.com", null);
        //==============================================================
    }
    @Subscribe
    public void onConnect(GuildedWebSocketWelcomeEvent e)
    {
        System.out.println("Connected to Guilded WebSocket server.");
    }
    @Subscribe
    public void onDisconnect(GuildedWebSocketClosedEvent e)
    {
        System.out.println("Disconnected from Guilded WebSocket server.");
    }
    @Subscribe
    public void onUnknownGuildedEvent(UnknownGuildedEvent e)
    {
        System.err.println("===== Unknown Guilded event =====\nRaw: " + e.getRawString() + "\nReason: " + e.getReason());
        if(e.getReason() != null) e.getReason().printStackTrace();
    }
}
