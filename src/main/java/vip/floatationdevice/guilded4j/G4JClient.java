/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.event.GuildedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketClosedEvent;
import vip.floatationdevice.guilded4j.event.GuildedWebSocketWelcomeEvent;
import vip.floatationdevice.guilded4j.event.ResumeEvent;
import vip.floatationdevice.guilded4j.exception.RestManagerCreationException;
import vip.floatationdevice.guilded4j.object.misc.Bot;
import vip.floatationdevice.guilded4j.rest.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.util.ArrayList;

/**
 * Guilded4J's main component, can send HTTP requests to the API, receive WebSocket events and post them as {@link vip.floatationdevice.guilded4j.event.GuildedEvent}s.
 * @see G4JWebSocketClient
 * @see vip.floatationdevice.guilded4j.event.GuildedEvent
 */
public class G4JClient
{
    public static final String
            CHANNELS_URL = "https://www.guilded.gg/api/v1/channels",
            CALENDAR_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/events",
            CALENDAR_RSVP_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/events/{calendarEventId}/rsvps",
            SERVER_URL = "https://www.guilded.gg/api/v1/servers/{serverId}",
            MSG_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/messages",
            NICKNAME_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/nickname",
            MEMBERS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members",
            BANS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/bans",
            FORUM_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/topics",
            LIST_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/items",
            DOC_CHANNEL_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/docs",
            USER_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/xp",
            ROLE_XP_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/roles/{roleId}/xp",
            SOCIAL_LINK_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/social-links/{type}",
            GROUP_URL = "https://www.guilded.gg/api/v1/groups/{groupId}/members/{userId}",
            ROLES_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/members/{userId}/roles",
            REACTION_URL = "https://www.guilded.gg/api/v1/channels/{channelId}/content/{contentId}/emotes/{emoteId}",
            WEBHOOKS_URL = "https://www.guilded.gg/api/v1/servers/{serverId}/webhooks";
    private final ArrayList<RestManager> managers = new ArrayList<>(); // contains all the REST managers
    private final EventBus bus = new EventBus();
    private final String token;
    public boolean verboseEnabled = false, autoReconnect = false;
    public G4JWebSocketClient ws;
    int httpTimeout = 20000;
    private String lastMessageId = null;
    private Proxy proxy = Proxy.NO_PROXY;

    public G4JClient(String token)
    {
        this.token = token;
        ws = new G4JWebSocketClient(token);
    }

    public G4JClient(String token, String lastMessageId)
    {
        this.token = token;
        ws = new G4JWebSocketClient(token, lastMessageId);
    }
//============================== API FUNCTIONS START ==============================

    /**
     * Gets the calendar event manager.
     */
    public CalendarEventManager getCalendarEventManager()
    {
        return (CalendarEventManager) getManagerOrCreate(CalendarEventManager.class);
    }

    /**
     * Gets the chat message manager.
     */
    public ChatMessageManager getChatMessageManager()
    {
        return (ChatMessageManager) getManagerOrCreate(ChatMessageManager.class);
    }

    /**
     * Gets the doc manager.
     */
    public DocManager getDocManager()
    {
        return (DocManager) getManagerOrCreate(DocManager.class);
    }

    /**
     * Gets the forum thread manager.
     */
    public ForumManager getForumManager()
    {
        return (ForumManager) getManagerOrCreate(ForumManager.class);
    }

    /**
     * Gets the group manager.
     */
    public GroupManager getGroupManager()
    {
        return (GroupManager) getManagerOrCreate(GroupManager.class);
    }

    /**
     * Gets the list item manager.
     */
    public ListItemManager getListItemManager()
    {
        return (ListItemManager) getManagerOrCreate(ListItemManager.class);
    }

    /**
     * Gets the server member manager.
     */
    public MemberManager getMemberManager()
    {
        return (MemberManager) getManagerOrCreate(MemberManager.class);
    }

    /**
     * Gets the reaction manager.
     */
    public ReactionManager getReactionManager()
    {
        return (ReactionManager) getManagerOrCreate(ReactionManager.class);
    }

    /**
     * Gets the role manager.
     */
    public RoleManager getRoleManager()
    {
        return (RoleManager) getManagerOrCreate(RoleManager.class);
    }

    /**
     * Gets the server manager.
     */
    public ServerManager getServerManager()
    {
        return (ServerManager) getManagerOrCreate(ServerManager.class);
    }

    /**
     * Gets the channel manager.
     */
    public ServerChannelManager getServerChannelManager()
    {
        return (ServerChannelManager) getManagerOrCreate(ServerChannelManager.class);
    }

    /**
     * Gets the webhook manager.
     */
    public WebhookManager getWebhookManager()
    {
        return (WebhookManager) getManagerOrCreate(WebhookManager.class);
    }

    /**
     * Gets the XP manager.
     */
    public XPManager getXPManager()
    {
        return (XPManager) getManagerOrCreate(XPManager.class);
    }

//============================== API FUNCTIONS END ==============================

//============================== INTERNAL EVENT LISTENER START ==============================

    @Subscribe
    public void onConnect(GuildedWebSocketWelcomeEvent e)
    {
        Bot self = e.getSelf();
        if(verboseEnabled)
            System.out.println("[G4JClient] WebSocket client logged in (last message ID: " + e.getLastMessageId() + ", heartbeat: " + e.getHeartbeatInterval() + "ms)" +
                    "\n Logged in as " + self.getName() + " (user ID: " + self.getId() + ", bot ID: " + self.getBotId() + ", home server ID: " + e.getServerID() + ")"
            );
    }

    @Subscribe
    public void onResumeEvent(ResumeEvent e)
    {
        if(verboseEnabled)
            System.out.println("[G4JClient] Recovered previous session");
    }

    @Subscribe
    private void onGuildedEvent(GuildedEvent e)
    {
        lastMessageId = e.getEventID(); // update lastMessageId for future use (if auto reconnect is enabled)
    }

    @Subscribe
    private void onDisconnect(GuildedWebSocketClosedEvent e)
    {
        if(verboseEnabled)
            System.out.println("[G4JClient] Connection closed " + (e.isRemote() ? "(by remote peer) " : "") + (e.isUnexpected() ? "(by error)" : "") +
                    "\n Code: " + e.getCode() + ", reason: " + e.getReason()
            );
        if(e.isUnexpected() && autoReconnect) // auto reconnect?
        {
            if(e.getCode() == 1007)
            {
                if(verboseEnabled)
                    System.err.println("[G4JClient] Unable to recover the previous session. Establish clean connection");
                Util.runAsyncTaskLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        connectWebSocket(false, null);
                    }
                }, 1000);
            }
            else
            {
                if(verboseEnabled) System.err.println("[G4JClient] Connection lost. Attempting to reconnect");
                Util.runAsyncTaskLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        connectWebSocket(false, lastMessageId);
                    }
                }, 1000);
            }
        }
    }

//============================== INTERNAL EVENT LISTENER END ==============================

    /**
     * Set the timeout of the HTTP request.
     * @param timeoutMs The timeout in milliseconds.
     */
    public void setHttpTimeout(int timeoutMs)
    {
        if(timeoutMs < 0) throw new IllegalArgumentException("HTTP request timeout cannot be negative");
        this.httpTimeout = timeoutMs;
        for(RestManager m : managers) m.setHttpTimeout(httpTimeout);
    }

    /**
     * Set the proxy to be used for any network connections (HTTP and WebSocket).
     * @param proxy The proxy to use. If null or Proxy.NO_PROXY, no proxy will be used.
     */
    public void setProxy(Proxy proxy)
    {
        this.proxy = proxy == null ? Proxy.NO_PROXY : proxy;
        for(RestManager m : managers) m.setProxy(proxy);
        ws.setProxy(this.proxy);
    }

    /**
     * Toggle verbose mode.
     * @param status If set to true, the HTTP requests and responses, and received WebSocket messages will be printed to stdout.
     */
    public G4JClient setVerbose(boolean status)
    {
        verboseEnabled = status;
        for(RestManager m : managers) m.setVerbose(status);
        ws.setVerbose(status);
        return this;
    }

    /**
     * Toggles auto reconnect of WebSocket event manager.
     * @param status If set to true, the WebSocket event manager will attempt to reconnect when the connection is closed unexpectedly.
     */
    public G4JClient setAutoReconnect(boolean status)
    {
        autoReconnect = status;
        return this;
    }

    /**
     * Gets the specified REST manager. If the manager is not initialized, create it and add it to the managers list.
     * @param clazz The REST manager class.
     * @return The REST manager (newly created or already existing).
     * @throws RestManagerCreationException If the REST manager creation fails.
     */
    private RestManager getManagerOrCreate(Class<? extends RestManager> clazz)
    {
        for(RestManager m : managers)
            if(m.getClass() == clazz) return m;
        RestManager newManager;
        try
        {
            // new manager(token).setHttpTimeout(httpTimeout).setProxy(proxy).setVerbose(verboseEnabled)
            Constructor<? extends RestManager> constructor = clazz.getConstructor(String.class);
            newManager = constructor.newInstance(token).setHttpTimeout(httpTimeout).setProxy(proxy).setVerbose(verboseEnabled);
        }
        catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            throw new RestManagerCreationException("Failed to create REST manager " + clazz.getName(), e);
        }
        managers.add(newManager);
        return newManager;
    }

    /**
     * Register an event listener to the WebSocket event manager.
     * @param listener The event listener object.
     */
    public G4JClient registerEventListener(Object listener)
    {
        bus.register(listener);
        return this;
    }

    /**
     * Unregister an event listener from the WebSocket event manager.
     * @param listener The event listener.
     */
    public G4JClient unregisterEventListener(Object listener)
    {
        bus.unregister(listener);
        return this;
    }

    /**
     * Get the built-in WebSocket event manager.
     */
    public G4JWebSocketClient getWebSocketClient()
    {
        if(ws == null)
        {
            ws = new G4JWebSocketClient(token).setVerbose(verboseEnabled);
            ws.eventBus = bus;
        }
        return ws;
    }

    /**
     * Connect to the Guilded WebSocket server and start receiving events.
     * If the connection is already opened, do nothing.
     */
    public G4JClient connectWebSocket(boolean blocking, String lastMessageId)
    {
        try
        {
            if(ws != null && ws.isOpen()) return this;
            ws = new G4JWebSocketClient(token, lastMessageId).setVerbose(verboseEnabled);
            ws.eventBus = bus;
            registerEventListener(this);
            if(blocking) ws.connectBlocking();
            else ws.connect();
        }
        catch(InterruptedException ignored) {}
        return this;
    }

    /**
     * Same as connectWebSocket(false, lastMessageId).
     */
    public G4JClient connectWebSocket(String lastMessageId)
    {
        return connectWebSocket(false, lastMessageId);
    }

    /**
     * Same as connectWebSocket(false, null).
     */
    public G4JClient connectWebSocket()
    {
        return connectWebSocket(false, null);
    }

    /**
     * Disconnect from Guilded WebSocket server.
     * If the connection is not opened, do nothing.
     */
    public G4JClient disconnectWebSocket(boolean blocking)
    {
        if(ws != null && !ws.isClosing() && !ws.isClosed())
            try
            {
                if(blocking) ws.closeBlocking();
                else ws.close();
            }
            catch(InterruptedException ignored) {}
        return this;
    }

    /**
     * Same as disconnectWebSocket(false).
     */
    public G4JClient disconnectWebSocket()
    {
        return disconnectWebSocket(false);
    }
}
