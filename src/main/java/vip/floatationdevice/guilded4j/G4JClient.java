/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import vip.floatationdevice.guilded4j.exception.RestManagerCreationException;
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

    String authToken;
    int httpTimeout = 20000;
    Proxy proxy = Proxy.NO_PROXY;

    private final ArrayList<RestManager> managers = new ArrayList<>(); // contains all the REST managers

    /**
     * Built-in WebSocket event manager ({@link G4JWebSocketClient}).
     */
    public G4JWebSocketClient ws;

    public G4JClient(String authToken)
    {
        this.authToken = authToken;
        ws = new G4JWebSocketClient(authToken);
    }

    public G4JClient(String authToken, String lastMessageId)
    {
        this.authToken = authToken;
        ws = new G4JWebSocketClient(authToken, lastMessageId);
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

    /**
     * Initialize or reset Guilded bot access token.
     * @param token The bot API access token (without "Bearer" prefix).
     */
    public void setAuthToken(String token)
    {
        authToken = token;
        ws.setAuthToken(authToken);
        for(RestManager m : managers) m.setAuthToken(authToken);
    }

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
            // new manager(token).setHttpTimeout(httpTimeout).setProxy(proxy)
            Constructor<? extends RestManager> constructor = clazz.getConstructor(String.class);
            newManager = constructor.newInstance(authToken).setHttpTimeout(httpTimeout).setProxy(proxy);
        }
        catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            throw new RestManagerCreationException("Failed to create REST manager " + clazz.getName(), e);
        }
        managers.add(newManager);
        return newManager;
    }
}
