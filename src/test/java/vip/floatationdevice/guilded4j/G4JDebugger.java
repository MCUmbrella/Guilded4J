/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.enums.ServerChannelType;
import vip.floatationdevice.guilded4j.enums.SocialMedia;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.*;
import vip.floatationdevice.guilded4j.object.misc.Bot;
import vip.floatationdevice.guilded4j.object.misc.MemberRoleSummary;

import java.io.*;
import java.util.*;

/**
 * A built-in CLI tool for testing and debugging Guilded4J library.
 */
@SuppressWarnings({"unused"})
public class G4JDebugger
{
    final static String helpIndexText = datePfx() + " [i] Usage: help <pageNum>\n" +
            " 0: Basic\n" +
            " 1: Chat channel management\n" +
            " 2: Forum channel management\n" +
            " 3: List channel management\n" +
            " 4: XP management\n" +
            " 5: Reaction management\n" +
            " 6: Group management\n" +
            " 7: Role management\n" +
            " 8: Server member management\n" +
            " 9: Channel management\n";
    final static String[] helpText = new String[]{
            datePfx() + " [i] Basic:\n" +
                    " > exit\n" +
                    "    Log out, save the current session and exit\n" +
                    " > disconnect\n" +
                    "    Close WebSocket connection\n" +
                    " > reconnect\n" +
                    "    Reconnect WebSocket\n" +
                    " > pwd\n" +
                    "    Print the current channel and server UUID\n" +
                    " > cd [UUID]\n" +
                    "    Change/clear the target channel UUID\n" +
                    " > scd [ID]\n" +
                    "    Change/clear the target server ID\n" +
                    " > save\n" +
                    "    Save the session now\n" +
                    " > dump\n" +
                    "    Toggle dump command result & WebSocket events\n" +
                    " > token <AuthToken>\n" +
                    "    Update AuthToken\n" +
                    " > mem\n" +
                    "    Print memory usage\n" +
                    " > gc\n" +
                    "    Run garbage collector\n" +
                    " > whoami\n" +
                    "    Print the current bot's info\n",
            datePfx() + " [i] Chat channel management:\n" +
                    " > ls\n" +
                    "    List messages in the current channel\n" +
                    " > send <text>\n" +
                    "    Send a chat message\n" +
                    " > reply\n" +
                    "    Make a reply to up to 5 messages\n" +
                    " > rm <(UUID)messageId>\n" +
                    "    Delete a message with specified UUID\n" +
                    " > update <(UUID)messageId> <text>\n" +
                    "    Update a message with specified UUID\n" +
                    " > get <(UUID)messageId>\n" +
                    "    Get the raw message object string from specified UUID\n",
            datePfx() + " [i] Forum channel management:\n" +
                    " > mkthread\n" +
                    "    Create a forum thread in the forum channel\n",
            datePfx() + " [i] List channel management:\n" +
                    " > lsitem\n" +
                    "    List items in the current channel\n" +
                    " > mkitem <message>\n" +
                    "    Create a list item in the list channel\n" +
                    " > rmitem <(UUID)itemId>\n" +
                    "    Remove a list item with specified UUID\n" +
                    " > updateitem <(UUID)itemId> <message>\n" +
                    "    Update a list item with specified UUID\n" +
                    " > getitem <(UUID)itemId>\n" +
                    "    Get the information of a list item with specified UUID\n",
            datePfx() + " [i] XP management:\n" +
                    " > addxp <userId> <(int)amount>\n" +
                    "    Add XP to specified user\n" +
                    " > addrolexp <(int)roleId> <(int)amount>\n" +
                    "    Add XP to all users with specified role\n",
            datePfx() + " [i] Reaction management:\n" +
                    " > react <(?)contentId> <(int)emoteId>\n" +
                    "    Add a reaction to specified content\n" +
                    " > rmreact <(?)contentId> <(int)emoteId>\n" +
                    "    Remove a reaction from specified content\n",
            datePfx() + " [i] Group management:\n" +
                    " > groupadd <groupId> <userId>\n" +
                    "    Add a user to a group\n" +
                    " > groupkick <groupId> <userId>\n" +
                    "    Remove a user from a group\n",
            datePfx() + " [i] Role management:\n" +
                    " > lsrole <userId>\n" +
                    "    Print the specified user's role ID(s)\n" +
                    " > roleadd <(int)roleId> <userId>\n" +
                    "    Give the specified role to specified user\n" +
                    " > rolekick <(int)roleId> <userId>\n" +
                    "    Remove the specified role from specified user\n",
            datePfx() + " [i] Server member management:\n" +
                    " > lsmember\n" +
                    "    List members in the current server\n" +
                    " > member <userId>\n" +
                    "    Get the information of the specified member\n" +
                    " > nick <userId> <nickname>\n" +
                    "    Set the specified user's nickname\n" +
                    " > rmnick <userId>\n" +
                    "    Remove the specified user's nickname\n" +
                    " > social <userId> <socialMediaType>\n" +
                    "    Get the social media link of the specified user.\n" +
                    "    Available: twitch, bnet, psn, xbox, steam, origin,\n" +
                    "    youtube, twitter, facebook, switch, patreon, roblox\n" +
                    " > kick <userId>\n" +
                    "    Kick the specified user from the current server\n" +
                    " > lsban\n" +
                    "    List bans in the current server\n" +
                    " > ban <userId> [reason]\n" +
                    "    Ban the specified user from the current server\n" +
                    " > unban <userId>\n" +
                    "    Unban the specified user from the current server\n" +
                    " > getban <userId>\n" +
                    "    Get the ban information of the specified user\n",
            datePfx() + " [i] Channel management:\n" +
                    " > mkch\n" +
                    "    Create a new channel\n" +
                    " > channel <channelId>\n" +
                    "    Get the information of the specified channel\n"
    };
    static Boolean dumpEnabled = false;
    final static Scanner scanner = new Scanner(System.in);
    static String workChannel = "";
    static String workServer = "";
    static G4JClient client;
    static String token;
    static Bot self;

    static class GuildedEventListener
    {
        @Subscribe
        public void onInit(GuildedWebSocketWelcomeEvent e)
        {
            System.out.println("\n" + datePfx() + " [i] WebSocket client logged in (last message ID: " + e.getLastMessageId() + ", heartbeat: " + e.getHeartbeatInterval() + "ms)");
            self = e.getSelf();
            System.out.print(datePfx() + " [i] Logged in as " + self.getName() + " (user ID: " + self.getId() + ", bot ID: " + self.getBotId() + ", home server ID: " + e.getServerID() + ")" + prompt());
            client.ws.setHeartbeatInterval(e.getHeartbeatInterval());
        }

        @Subscribe
        public void onDisconnect(GuildedWebSocketClosedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] Connection closed " + (e.isRemote() ? "by remote peer (" : "(") + e.getCode() + ")\n    " + e.getReason() + prompt());
        }

        @Subscribe
        public void onMsg(ChatMessageCreatedEvent e)
        {
            ChatMessage m = e.getChatMessageObject();
            System.out.print(parseMessage(m, true));
        }

        @Subscribe
        public void onXP(TeamXpAddedEvent e)
        {
            if(dumpEnabled)
                System.out.print("\n" + datePfx() + " [D] " + Arrays.toString(e.getUserIds()) + ": +" + e.getXpAmount() + " XP" + prompt());
        }

        @Subscribe
        public void onNicknameChange(TeamMemberUpdatedEvent e)
        {
            if(dumpEnabled)
                System.out.print("\n" + datePfx() + " [D] " + e.getUserInfo().getUserId() + (e.getUserInfo().getNickname() == null ? ": nickname cleared" : ": nickname changed to '" + e.getUserInfo().getNickname() + "'") + prompt());
        }

        @Subscribe
        public void onRoleChange(TeamRolesUpdatedEvent e)
        {
            MemberRoleSummary[] users = e.getMembers();
            if(dumpEnabled)
            {
                System.out.println("\n" + datePfx() + " [D] Member role changes:");
                for(MemberRoleSummary user : users)
                    System.out.println("    " + user.getUserId() + ": " + Arrays.toString(user.getRoleIds()));
                System.out.print(prompt());
            }
        }

        @Subscribe
        public void onMemberJoined(TeamMemberJoinedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] " + e.getMember().getUser().getName() + " (ID: " + e.getMember().getUser().getId() + ") joined the server " + e.getServerID() + prompt());
        }

        @Subscribe
        public void onMemberRemoved(TeamMemberRemovedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] User with ID " + e.getUserId() + " removed from server " + e.getServerID() + " (cause: " + e.getCause() + ")" + prompt());
        }

        @Subscribe
        public void onBan(TeamMemberBannedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] User with ID " + e.getServerMemberBan().getUser().getId() + " in server " + e.getServerID() + " got banned. Reason: " + e.getServerMemberBan().getReason() + prompt());
        }

        @Subscribe
        public void onUnban(TeamMemberUnbannedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] User with ID " + e.getServerMemberBan().getUser().getId() + " unbanned from server " + e.getServerID() + prompt());
        }

        @Subscribe
        public void onUnknownGuildedEvent(UnknownGuildedEvent e)
        {
            System.err.print("\n" + datePfx() + " [!] Unknown event received: \n" + new JSONObject(e.getRawString()).toStringPretty() + prompt());
        }
    }

    static boolean workChannelValid()
    {
        try
        {
            UUID.fromString(workChannel);
            return true;
        }
        catch(Exception e)
        {
            System.err.println(datePfx() + "[X] Specify a channel UUID first");
            return false;
        }
    }

    static boolean workServerValid()
    {
        if(workServer.length() == 8)
            return true;
        else
        {
            System.err.println(datePfx() + "[X] Specify a server ID first");
            return false;
        }
    }

    static String resultPfx(){return datePfx() + " [D] Result:\n";}

    static String datePfx(){return "[" + DateUtil.date() + "]";}

    static String parseMessage(ChatMessage m, Boolean prompt)
    {
        return "\n[" + DateUtil.parse(m.getCreationTime()) + "] [" + m.getServerId() + "] [" + m.getChannelId() + "] (" + m.getId() + ") <" + m.getCreatorId() + "> " + m.getContent() + (prompt ? prompt() : "");
    }

    static String prompt(){return "\n[/" + workServer + "/" + workChannel + "] #";}

    static class G4JSession implements Serializable
    {
        public static final long serialVersionUID = 2L;
        public String savedToken;
        public String savedChannelId;
        public String savedServerId;

        public void save()
        {
            try
            {
                this.savedToken = token;
                this.savedChannelId = workChannel;
                this.savedServerId = workServer;
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("G4JSession.dat"));
                o.writeObject(this);
                o.close();
                System.out.println(datePfx() + " [i] Session saved\n" + this);
            }
            catch(Exception e)
            {
                System.err.println(datePfx() + " [X] Failed to save session: " + e);
            }
        }

        public Boolean restore()
        {
            try
            {
                ObjectInputStream i = new ObjectInputStream(new FileInputStream("G4JSession.dat"));
                G4JSession session = (G4JSession) i.readObject();
                this.savedToken = session.savedToken;
                this.savedChannelId = session.savedChannelId;
                this.savedServerId = session.savedServerId;
                i.close();
                System.out.println(datePfx() + " [i] Session restored\n" + this);
                return true;
            }
            catch(FileNotFoundException e) {return false;}
            catch(Exception e)
            {
                System.err.println(datePfx() + " [X] Failed to restore session: " + e);
                return false;
            }
        }

        @Override
        public String toString()
        {
            String tokenSummary = savedToken.length() < 10 ? "***" : savedToken.substring(0, 5) + "..." + savedToken.substring(savedToken.length() - 5);
            return "Token: " + tokenSummary + "\n" +
                    "Channel UUID: " + savedChannelId + "\n" +
                    "Server ID: " + savedServerId;
        }
    }

    public static void main(String[] args)
    {
        G4JSession session = new G4JSession();
        if(session.restore())
        {
            token = session.savedToken;
            workChannel = session.savedChannelId;
            workServer = session.savedServerId;
            client = new G4JClient(token);
        }
        else
        {
            System.out.print("Enter AuthToken: ");
            token = scanner.nextLine();
            if(token.length() < 10)
                System.err.println("[!] The length of this token is suspiciously short.\n    Make sure you copy it correctly. The program will proceed anyway.\n    You can reset it anytime using the 'token' command.");
            client = new G4JClient(token);
            System.out.println(datePfx() + " [i] Logging in");
        }
        client.ws.eventBus.register(new GuildedEventListener());
        System.out.println(datePfx() + " [i] Connecting to Guilded");
        client.ws.connect();
        String text = null;//typed command
        String textCache = "";//previous command
        for(; ; System.out.print(prompt()))
        {
            try
            {
                text = scanner.nextLine();
                if(text.equals("!!")) {text = textCache;}
                String[] commands = text.split(" ");
                switch(commands[0].toLowerCase())
                {
                    case "help":
                    {
                        if(commands.length == 2)
                            System.out.print(helpText[Integer.parseInt(commands[1])]);
                        else
                            System.out.print(helpIndexText);
                        break;
                    }
                    case "exit":
                    {
                        System.out.println(datePfx() + " [i] Exiting");
                        client.ws.close();
                        session.save();
                        System.exit(0);
                    }
                    case "disconnect":
                    {
                        System.out.print(datePfx() + " [i] Disconnecting");
                        client.ws.close();
                        break;
                    }
                    case "reconnect":
                    {
                        System.out.print(datePfx() + " [i] Reconnecting");
                        if(client.ws != null) {client.ws.reconnect();}
                        break;
                    }
                    case "pwd":
                    {
                        System.out.print(datePfx() + " [i] Channel: " + (workChannel.isEmpty() ? "not set" : workChannel) + ". Server: " + (workServer.isEmpty() ? "not set" : workServer));
                        break;
                    }
                    case "cd":
                    {
                        if(commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            workChannel = commands[1];
                            System.out.print(datePfx() + " [i] Channel set to " + workChannel);
                        }
                        else
                        {
                            workChannel = "";
                            System.out.println(datePfx() + " [i] Channel cleared");
                        }
                        break;
                    }
                    case "scd":
                    {
                        if(commands.length == 2)
                        {
                            if(commands[1].length() == 8)
                            {
                                workServer = commands[1];
                                System.out.print(datePfx() + " [i] Server set to " + workServer);
                            }
                            else System.err.println(datePfx() + " [X] Invalid server ID");
                        }
                        else
                        {
                            workServer = "";
                            System.out.println(datePfx() + " [i] Server cleared");
                        }
                        break;
                    }
                    case "save":
                    {
                        session.save();
                        break;
                    }
                    case "dump":
                    {
                        dumpEnabled = !dumpEnabled;
                        System.out.print(datePfx() + " [i] Dump status changed to: " + client.ws.toggleDump());
                        break;
                    }
                    case "token":
                    {
                        if(commands.length == 2)
                        {
                            token = commands[1];
                            client.setAuthToken(commands[1]);
                            client.ws.setAuthToken(commands[1]);
                            System.out.print(datePfx() + " [i] Updated AuthToken");
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: token <token>");
                        break;
                    }
                    case "ls":
                    {
                        if(workChannelValid())
                        {
                            ChatMessage[] msgs = client.getChatMessageManager().getChannelMessages(workChannel);
                            for(int i = msgs.length - 1; i >= 0; i--) System.out.print(parseMessage(msgs[i], false));
                        }
                        break;
                    }
                    case "send":
                    {
                        if(workChannelValid() && commands.length > 1)
                        {
                            String result = client.getChatMessageManager().createChannelMessage(workChannel, text.substring(5), null, null, null, null).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "reply":
                    {
                        if(workChannelValid())
                        {
                            System.out.println("[i] UUID of the message(s) replying to: (1 UUID per line, empty line to end)");
                            ArrayList<String> uuids = new ArrayList<String>();
                            Scanner s = new Scanner(System.in);
                            String raw;
                            boolean isPrivate;
                            boolean isSilent;
                            for(int a = 0; a != 5; a++)
                            {
                                System.out.print("? ");
                                raw = s.nextLine();
                                if(raw.isEmpty()) break;
                                UUID.fromString(raw);
                                if(!uuids.contains(raw)) uuids.add(raw);
                            }
                            String[] uuidArray = new String[uuids.size()];
                            uuids.toArray(uuidArray);
                            if(uuidArray.length == 0)
                            {
                                System.out.print("[X] No UUID given");
                                continue;
                            }
                            System.out.print("[i] Reply privately? [true/false]\n? ");
                            isPrivate = Boolean.parseBoolean(s.nextLine());
                            System.out.print("[i] Reply silently? [true/false]\n? ");
                            isSilent = Boolean.parseBoolean(s.nextLine());
                            System.out.print("[i] Message content:\n? ");
                            String result = client.getChatMessageManager().createChannelMessage(workChannel, s.nextLine(), null, uuidArray, isPrivate, isSilent).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "rm":
                    {
                        if(workChannelValid() && commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            client.getChatMessageManager().deleteChannelMessage(workChannel, commands[1]);
                        }
                        break;
                    }
                    case "update":
                    {
                        if(workChannelValid() && commands.length > 2)
                        {
                            UUID.fromString(commands[1]);
                            ChatMessage result = client.getChatMessageManager().updateChannelMessage(workChannel, commands[1], text.substring(44), null);
                            if(dumpEnabled)
                                System.out.print(resultPfx() + new JSONObject(result.toString()).toStringPretty());
                        }
                        break;
                    }
                    case "get":
                    {
                        if(commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            System.out.print(new JSONObject(client.getChatMessageManager().getMessage(workChannel, commands[1]).toString()).toStringPretty());
                        }
                        break;
                    }
                    case "mkthread":
                    {
                        if(workChannelValid())
                        {
                            Scanner s = new Scanner(System.in);
                            String title, content;
                            System.out.print("[i] Enter title:\n? ");
                            title = s.nextLine();
                            if(title.length() < 1)
                            {
                                System.err.println("[X] Title too short");
                                continue;
                            }
                            System.out.print("[i] Enter content:\n? ");
                            content = s.nextLine();
                            if(content.length() < 1)
                            {
                                System.err.println("[X] Content too short");
                                continue;
                            }
                            String result = client.getForumManager().createForumThread(workChannel, title, content).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "lsitem":
                    {
                        if(workChannelValid())
                        {
                            ListItemSummary[] items = client.getListItemManager().getListItems(workChannel);
                            for(ListItemSummary item : items)
                                System.out.println("==============================\n" +
                                        "  - Message: " + item.getMessage() + "\n" +
                                        "  - Note:\n" +
                                        "      Created at: " + item.getNote().getCreationTime() + "\n" +
                                        "      Created by: " + item.getNote().getCreatorId() + "\n" +
                                        "  - ID: " + item.getId() + "\n" +
                                        "  - Created at: " + item.getCreationTime() + "\n" +
                                        "  - Created by: " + item.getCreatorId() + "\n" +
                                        "  - Updated at: " + item.getUpdateTime() + "\n" +
                                        "  - Updated by: " + item.getUpdaterId() + "\n" +
                                        "  - Completed at: " + item.getCompletionTime() + "\n" +
                                        "  - Completed by: " + item.getCompleterId()
                                );
                        }
                        break;
                    }
                    case "mkitem":
                    {
                        if(workChannelValid() && commands.length > 1)
                        {
                            String message = text.substring(commands[0].length() + 1);
                            System.out.print("[i] Enter the note (optional):\n? ");
                            String note = new Scanner(System.in).nextLine();
                            if(note.isEmpty()) note = null;
                            ListItem result = client.getListItemManager().createListItem(workChannel, message, note);
                            System.out.print(datePfx() + " [i] Item created. ID: " + result.getId());
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result.toString()).toStringPretty());
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: mkitem <message>");
                        break;
                    }
                    case "rmitem":
                    {
                        if(workChannelValid() && commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            client.getListItemManager().deleteListItem(workChannel, commands[1]);
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: rmitem <(UUID)itemId>");
                        break;
                    }
                    case "updateitem":
                    {
                        if(workChannelValid() && commands.length > 2)
                        {
                            UUID.fromString(commands[1]);
                            String message = text.substring(commands[0].length() + commands[1].length() + 2);
                            System.out.print("[i] Enter the note (optional):\n? ");
                            String note = new Scanner(System.in).nextLine();
                            if(note.isEmpty()) note = null;
                            String result = client.getListItemManager().updateListItem(workChannel, commands[1], message, note).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: updateitem <(UUID)itemId> <(String)message>");
                        break;
                    }
                    case "getitem":
                    {
                        if(workChannelValid() && commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            ListItem item = client.getListItemManager().getListItem(workChannel, commands[1]);
                            System.out.print(datePfx() + " [i] Item: " + item.getMessage() + "\n" +
                                    "  - Note:\n" +
                                    "      Content: " + item.getNote().getContent() + "\n" +
                                    "      Created at: " + item.getNote().getCreationTime() + "\n" +
                                    "      Created by: " + item.getNote().getCreatorId() + "\n" +
                                    "  - ID: " + item.getId() + "\n" +
                                    "  - Created at: " + item.getCreationTime() + "\n" +
                                    "  - Created by: " + item.getCreatorId() + "\n" +
                                    "  - Updated at: " + item.getUpdateTime() + "\n" +
                                    "  - Updated by: " + item.getUpdaterId() + "\n" +
                                    "  - Completed at: " + item.getCompletionTime() + "\n" +
                                    "  - Completed by: " + item.getCompletedBy()
                            );
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: getitem <(UUID)itemId>");
                        break;
                    }
                    case "addxp":
                    {
                        if(workServerValid() && commands.length == 3 && commands[1].length() == 8)
                        {
                            int result = client.getXPManager().awardUserXp(workServer, commands[1], Integer.parseInt(commands[2]));
                            if(dumpEnabled) System.out.print(resultPfx() + result);
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: addxp <(string)userId> <(int)amount>");
                        break;
                    }
                    case "addrolexp":
                    {
                        if(commands.length == 3)
                        {
                            client.getXPManager().awardRoleXp(workServer, Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: addrolexp <(int)roleId> <(int)amount>");
                        break;
                    }
                    case "react":
                    {
                        if(workChannelValid() && commands.length == 3)
                            client.getReactionManager().createContentReaction(workChannel, commands[1], Integer.parseInt(commands[2]));
                        else
                            System.err.println(datePfx() + " [X] Usage: react <contentId> <(int)emoteId>");
                        break;
                    }
                    case "rmreact":
                    {
                        if(workChannelValid() && commands.length == 3)
                            client.getReactionManager().deleteContentReaction(workChannel, commands[1], Integer.parseInt(commands[2]));
                        else
                            System.err.println(datePfx() + " [X] Usage: rmreact <contentId> <(int)emoteId>");
                        break;
                    }
                    case "groupadd":
                    {
                        if(commands.length == 3 && commands[1].length() == 8 && commands[2].length() == 8)
                            client.getGroupManager().addGroupMember(commands[1], commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: groupadd <groupId> <userId>");
                        break;
                    }
                    case "groupkick":
                    {
                        if(commands.length == 3 && commands[1].length() == 8 && commands[2].length() == 8)
                            client.getGroupManager().removeGroupMember(commands[1], commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: groupkick <groupId> <userId>");
                        break;
                    }
                    case "lsrole":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                            System.out.print(Arrays.toString(client.getRoleManager().getMemberRoles(workServer, commands[1])));
                        else
                            System.err.println(datePfx() + " [X] Usage: lsrole <userId>");
                        break;
                    }
                    case "roleadd":
                    {
                        if(commands.length == 3 && commands[2].length() == 8)
                            client.getRoleManager().addRoleMember(workServer, Integer.parseInt(commands[1]), commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: roleadd <(int)roleId> <userId>");
                        break;
                    }
                    case "rolekick":
                    {
                        if(commands.length == 3 && commands[2].length() == 8)
                            client.getRoleManager().removeRoleMember(workServer, Integer.parseInt(commands[1]), commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: roleadd <(int)roleId> <userId>");
                        break;
                    }
                    case "lsmember":
                    {
                        if(workServerValid())
                        {
                            ServerMemberSummary[] members = client.getMemberManager().getServerMembers(workServer);
                            for(ServerMemberSummary member : members)
                                System.out.println("=============================="
                                        + "\n  - Name: " + member.getUser().getName()
                                        + "\n  - ID: " + member.getUser().getId()
                                        + "\n  - Type: " + member.getUser().getType()
                                        + "\n  - Roles: " + Arrays.toString(member.getRoleIds())
                                );
                        }
                        break;
                    }
                    case "member":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                        {
                            ServerMember member = client.getMemberManager().getServerMember(workServer, commands[1]);
                            System.out.print(datePfx() + " [i] Member " + commands[1] + ":\n"
                                    + "  - Nickname: " + member.getNickname() + "\n"
                                    + "  - Real name: " + member.getUser().getName() + "\n"
                                    + "  - User ID: " + member.getUser().getId() + "\n"
                                    + "  - Type: " + member.getUser().getType() + "\n"
                                    + "  - Roles: " + Arrays.toString(member.getRoleIds()) + "\n"
                                    + "  - Joined at: " + member.getJoinTime() + "\n"
                                    + "  - Registered at: " + member.getUser().getCreationTime() + "\n"
                            );
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: member <userId>");
                        break;
                    }
                    case "nick":
                    {
                        if(workServerValid() && commands.length > 2 && commands[1].length() == 8)
                        {
                            String result = client.getMemberManager().setMemberNickname(workServer, commands[1], text.substring(14));
                            if(dumpEnabled) System.out.print(resultPfx() + result);
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: nick <userId> <nickname>");
                        break;
                    }
                    case "rmnick":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                        {
                            String result = client.getMemberManager().setMemberNickname(workServer, commands[1], null);
                            if(dumpEnabled) System.out.print(resultPfx() + result);
                        }
                        break;
                    }
                    case "social":
                    {
                        if(workServerValid() && commands.length == 3 && commands[1].length() == 8)
                        {
                            HashMap<String, String> result = client.getMemberManager().getSocialLink(workServer, commands[1], SocialMedia.valueOf(commands[2].toUpperCase()));
                            System.out.print(resultPfx() + result);
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: social <userID> <socialMediaName>");
                        break;
                    }
                    case "kick":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                            client.getMemberManager().kickServerMember(workServer, commands[1]);
                        else
                            System.err.println(datePfx() + " [X] Usage: kick <userId>");
                        break;
                    }
                    case "lsban":
                    {
                        if(workServerValid())
                        {
                            ServerMemberBan[] bans = client.getMemberManager().getServerMemberBans(workServer);
                            for(ServerMemberBan ban : bans)
                                System.out.println("=============================="
                                        + "\n  - Name: " + ban.getUser().getName()
                                        + "\n  - ID: " + ban.getUser().getId()
                                        + "\n  - Reason: " + ban.getReason()
                                        + "\n  - Created at: " + ban.getCreationTime()
                                        + "\n  - Created by: " + ban.getCreatorId()
                                );
                        }
                        break;
                    }
                    case "ban":
                    {
                        if(workServerValid() && commands.length > 1 && commands[1].length() == 8)
                        {
                            String reason = commands.length == 2 ? null : text.substring(13);
                            ServerMemberBan ban = client.getMemberManager().banServerMember(workServer, commands[1], reason);
                            System.out.print("\n" + datePfx() + " [i] Banned" + ban.getUser().getName() + "\n"
                                    + "  - ID: " + ban.getUser().getId() + "\n"
                                    + "  - Reason: " + ban.getReason()
                            );
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: ban <userId> [reason]");
                        break;
                    }
                    case "unban":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                            client.getMemberManager().unbanServerMember(workServer, commands[1]);
                        else
                            System.err.println(datePfx() + " [X] Usage: unban <userId>");
                        break;
                    }
                    case "getban":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                        {
                            ServerMemberBan ban = client.getMemberManager().getServerMemberBan(workServer, commands[1]);
                            System.out.print(datePfx() + " [i] Ban for " + commands[1] + ":\n"
                                    + "  - Name: " + ban.getUser().getName() + "\n"
                                    + "  - ID: " + ban.getUser().getId() + "\n"
                                    + "  - Reason: " + ban.getReason() + "\n"
                                    + "  - Created at: " + ban.getCreationTime() + "\n"
                                    + "  - Created by: " + ban.getCreatorId()
                            );
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: getban <userId>");
                        break;
                    }
                    case "mkch":
                    {
                        if(workServerValid() && commands.length == 2)
                        {
                            String topic;
                            Boolean isPublic = null;
                            ServerChannelType type;
                            String groupId;
                            Integer categoryId = null;
                            System.out.print(datePfx() + " [i] Topic (empty for none):\n? ");
                            topic = scanner.nextLine();
                            System.out.print(datePfx() + " [i] Public? (true/false):\n? ");
                            try {isPublic = Boolean.parseBoolean(scanner.nextLine());}catch(Exception ignored) {}
                            System.out.print(datePfx() + " [i] Type: " + Arrays.toString(ServerChannelType.values()) + "\n? ");
                            type = ServerChannelType.fromString(scanner.nextLine());
                            System.out.print(datePfx() + " [i] Group ID (empty to create in server home):\n? ");
                            groupId = scanner.nextLine();
                            System.out.print(datePfx() + " [i] Category ID (empty to create on top level):\n? ");
                            try {categoryId = Integer.parseInt(scanner.nextLine());}catch(Exception ignored) {}
                            String result = client.getServerChannelManager().createServerChannel(commands[1], topic, isPublic, type, workServer, groupId, categoryId).toString();
                            if(dumpEnabled)
                                System.out.print(datePfx() + " [i] Result: " + result);
                        }
                        else System.err.println(datePfx() + " [X] Usage: mkch <name>");
                        break;
                    }
                    case "channel":
                    {
                        if(workServerValid() && commands.length == 2)
                            System.out.print(datePfx() + " [i] Channel " + commands[1] + ":\n" + new JSONObject(client.getServerChannelManager().getServerChannel(commands[1]).toString()).toStringPretty());
                        else System.err.println(datePfx() + " [X] Usage: channel <channelId>");
                        break;
                    }
                    case "mem":
                    {
                        System.out.print(datePfx() + " [i] Memory usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB");
                        break;
                    }
                    case "gc":
                    {
                        System.out.print(datePfx() + " [i] Garbage collection...");
                        System.gc();
                        break;
                    }
                    case "whoami":
                    {
                        System.out.print(datePfx() + " [i] Bot name: " + self.getName() +
                                "\n User ID: " + self.getId() + ", Bot UUID: " + self.getBotId() +
                                "\n Created at: " + self.getCreationTime() + ", created by user with ID: " + self.getCreator()
                        );
                    }
                    case "test":
                    {
                        break;
                    }
                    default:
                    {
                        System.out.print(datePfx() + " [!] Type 'help' to get available commands and usages");
                        break;
                    }
                }
            }
            catch(NoSuchElementException e)
            {
                System.out.println("\n" + datePfx() + " [i] Exiting");
                client.ws.close();
                session.save();
                break;
            }
            catch(GuildedException e)
            {
                System.err.println(datePfx() + " [X] Operation failed (" + e.getType() +")\n  " + e.getCode() + ": " + e.getDescription());
            }
            catch(Exception e)
            {
                StringWriter esw = new StringWriter();
                e.printStackTrace(new PrintWriter(esw));
                System.err.println(datePfx() + " [X] A Java runtime exception occurred while executing the command\n==========Begin stacktrace==========\n" + esw + "===========End stacktrace===========");
            }
            catch(Throwable e)
            {
                System.err.println(datePfx() + " [X] A fatal error occurred. Program will exit");
                e.printStackTrace();
                System.exit(-1);
            }
            if(!text.isEmpty()) textCache = text;
        }
    }
}
