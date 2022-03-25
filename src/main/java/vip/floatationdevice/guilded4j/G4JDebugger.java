/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.Subscribe;
import vip.floatationdevice.guilded4j.enums.SocialMedia;
import vip.floatationdevice.guilded4j.event.*;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.ChatMessage;
import vip.floatationdevice.guilded4j.object.User;

import java.io.*;
import java.util.*;

/**
 * A built-in CLI tool for testing and debugging Guilded4J library.
 */
@SuppressWarnings({"unused"})
public class G4JDebugger
{
    static Boolean dumpEnabled = false;
    final static Scanner scanner = new Scanner(System.in);
    static String workChannel = "";
    static String workServer = "";
    static G4JClient client;

    static class GuildedEventListener
    {
        @Subscribe
        public void onInit(GuildedWebSocketInitializedEvent e)
        {
            System.out.print("\n" + datePfx() + " [i] WebSocket client logged in (last message ID: " + e.getLastMessageId() + ", heartbeat: " + e.getHeartbeatInterval() + "ms)" + prompt());
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
            User[] users = e.getMembers();
            if(dumpEnabled)
            {
                System.out.println("\n" + datePfx() + " [D] Member role changes:");
                for(User user : users)
                    System.out.println("    " + user.getUserId() + ": " + Arrays.toString(user.getRoleIds()));
                System.out.print(prompt());
            }
        }

        @Subscribe
        public void UnknownGuildedEvent(UnknownGuildedEvent e)
        {
            System.out.print("\n" + datePfx() + " [!] Unknown event received: \n" + new JSONObject(e.getRawString()).toStringPretty() + prompt());
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

    static String resultPfx(){return datePfx() + "[D] Result:\n";}

    static String datePfx(){return "[" + DateUtil.date() + "]";}

    static String parseMessage(ChatMessage m, Boolean prompt)
    {
        return "\n[" + DateUtil.parse(m.getCreationTime()) + "] [" + m.getServerId() + "] [" + m.getChannelId() + "] (" + m.getMsgId() + ") <" + m.getCreatorId() + "> " + m.getContent() + (prompt ? prompt() : "");
    }

    static String prompt(){return "\n[" + workServer + "/" + workChannel + "] #";}

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
                this.savedToken = client.authToken;
                this.savedChannelId = workChannel;
                this.savedServerId = workServer;
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("G4JSession.dat"));
                o.writeObject(this);
                o.close();
                System.out.println(datePfx() + " [i] Session saved");
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
                System.out.println(datePfx() + " [i] Session restored");
                return true;
            }
            catch(java.io.FileNotFoundException e) {return false;}
            catch(Exception e)
            {
                System.err.println(datePfx() + " [X] Failed to restore session: " + e);
                return false;
            }
        }
    }

    public static void main(String[] args)
    {
        G4JSession session = new G4JSession();
        String token;
        if(session.restore())
        {
            client = new G4JClient(session.savedToken);
            workChannel = session.savedChannelId;
            workServer = session.savedServerId;
        }
        else
        {
            System.out.print("Enter AuthToken: ");
            token = scanner.nextLine();
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
                        System.out.print(datePfx() + helpText);
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
                            ChatMessage[] msgs = client.getChannelMessages(workChannel);
                            for(int i = msgs.length - 1; i >= 0; i--) System.out.print(parseMessage(msgs[i], false));
                        }
                        break;
                    }
                    case "send":
                    {
                        if(workChannelValid() && commands.length > 1)
                        {
                            String result = client.createChannelMessage(workChannel, text.substring(5), null, null).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        else break;
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
                            System.out.println("[i] Private reply? [true/false]");
                            System.out.print("? ");
                            isPrivate = Boolean.parseBoolean(s.nextLine());
                            System.out.println("[i] Message content:");
                            System.out.print("? ");
                            String result = client.createChannelMessage(workChannel, s.nextLine(), uuidArray, isPrivate).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "rm":
                    {
                        if(workChannelValid() && commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            client.deleteChannelMessage(workChannel, commands[1]);
                        }
                        else break;
                    }
                    case "update":
                    {
                        if(workChannelValid() && commands.length > 2)
                        {
                            UUID.fromString(commands[1]);
                            ChatMessage result = client.updateChannelMessage(workChannel, commands[1], text.substring(44));
                            if(dumpEnabled)
                                System.out.print(resultPfx() + new JSONObject(result.toString()).toStringPretty());
                        }
                        else break;
                    }
                    case "get":
                    {
                        if(commands.length == 2)
                        {
                            UUID.fromString(commands[1]);
                            System.out.print(new JSONObject(client.getMessage(workChannel, commands[1]).toString()).toStringPretty());
                        }
                        break;
                    }
                    case "mkitem":
                    {
                        if(workChannelValid())
                        {
                            Scanner s = new Scanner(System.in);
                            String message, note;
                            System.out.print("[i] Enter the list item's display message:\n? ");
                            message = s.nextLine();
                            if(message.isEmpty())
                            {
                                System.err.println("[X] Message too short");
                                continue;
                            }
                            System.out.print("[i] Enter the note (optional):\n? ");
                            note = s.nextLine();
                            if(note.isEmpty()) note = null;
                            String result = client.createListItem(workChannel, message, note).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "addxp":
                    {
                        if(workServerValid() && commands.length == 3 && commands[1].length() == 8)
                        {
                            int result = client.awardUserXp(workServer, commands[1], Integer.parseInt(commands[2]));
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
                            client.awardRoleXp(workServer, Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: addrolexp <(int)roleId> <(int)amount>");
                        break;
                    }
                    case "react":
                    {
                        if(workChannelValid() && commands.length == 3)
                            client.createContentReaction(workChannel, commands[1], Integer.parseInt(commands[2]));
                        else
                            System.err.println(datePfx() + " [X] Usage: react <contentId> <(int)emoteId>");
                        break;
                    }
                    case "lsrole":
                    {
                        if(workServerValid() && commands.length == 2 && commands[1].length() == 8)
                            System.out.print(Arrays.toString(client.getMemberRoles(workServer, commands[1])));
                        else
                            System.err.println(datePfx() + " [X] Usage: lsrole <userId>");
                        break;
                    }
                    case "nick":
                    {
                        if(workServerValid() && commands.length > 2 && commands[1].length() == 8)
                        {
                            String result = client.setMemberNickname(workServer, commands[1], text.substring(14));
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
                            String result = client.setMemberNickname(workServer, commands[1], null);
                            if(dumpEnabled) System.out.print(resultPfx() + result);
                        }
                        break;
                    }
                    case "smlink":
                    {
                        if(workServerValid() && commands.length == 3 && commands[1].length() == 8)
                        {
                            HashMap<String, String> result = client.getSocialLink(workServer, commands[1], SocialMedia.valueOf(commands[2].toUpperCase()));
                            System.out.print(resultPfx() + result);
                        }
                        else
                            System.err.println(datePfx() + " [X] Usage: smlink <userID> <socialMediaName>");
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
                            String result = client.createForumThread(workChannel, title, content).toString();
                            if(dumpEnabled) System.out.print(resultPfx() + new JSONObject(result).toStringPretty());
                        }
                        break;
                    }
                    case "groupadd":
                    {
                        if(commands.length == 3 && commands[1].length() == 8 && commands[2].length() == 8)
                            client.addGroupMember(commands[1], commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: groupadd <groupId> <userId>");
                        break;
                    }
                    case "groupkick":
                    {
                        if(commands.length == 3 && commands[1].length() == 8 && commands[2].length() == 8)
                            client.removeGroupMember(commands[1], commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: groupkick <groupId> <userId>");
                        break;
                    }
                    case "roleadd":
                    {
                        if(commands.length == 3 && commands[2].length() == 8)
                            client.addRoleMember(workServer, Integer.parseInt(commands[1]), commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: roleadd <(int)roleId> <userId>");
                        break;
                    }
                    case "rolekick":
                    {
                        if(commands.length == 3 && commands[2].length() == 8)
                            client.removeRoleMember(workServer, Integer.parseInt(commands[1]), commands[2]);
                        else
                            System.err.println(datePfx() + " [X] Usage: roleadd <(int)roleId> <userId>");
                        break;
                    }
                    case "test":
                    {
                        //
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
                System.err.println(datePfx() + " [X] Operation failed\n    " + e.getCode() + ": " + e.getDescription());
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

    final static String helpText = "[" + DateUtil.date() + "] [i] COMMANDS:\n" +
            " > token <AuthToken>\n" +
            "    Update AuthToken\n" +
            " > disconnect\n" +
            " > reconnect\n" +
            " > dump\n" +
            "    Toggle dump command result & WebSocket events\n" +
            " > pwd\n" +
            "    Print the current channel UUID\n" +
            " > cd [UUID]\n" +
            "    Change/clear the target channel UUID\n" +
            " > send <string>\n" +
            "    Send the typed string\n" +
            " > reply\n" +
            "    Make a reply to up to 5 messages\n" +
            " > rm <UUID>\n" +
            "    Delete a message with specified UUID\n" +
            " > update <UUID> <string>\n" +
            "    Update a message with specified UUID\n" +
            " > get <UUID>\n" +
            "    Get the raw message object string from specified UUID\n" +
            " > mkitem\n" +
            "    Create a list item in the list channel\n" +
            " > addxp <(string)userId> <(int)amount>\n" +
            "    Add XP to specified user\n" +
            " > addrolexp <(int)roleId> <(int)amount>\n" +
            "    Add XP to all users with specified role\n" +
            " > react <(?)contentId> <(int)emoteId>\n" +
            "    Add a reaction to specified content\n" +
            " > lsrole <userId>\n" +
            "    Print the specified user's role ID(s)\n" +
            " > nick <userId> <(string)nickname>\n" +
            "    Set the specified user's nickname\n" +
            " > rmnick <userId>\n" +
            "    Remove the specified user's nickname\n" +
            " > smlink <userID> <(string)socialMediaName>\n" +
            "    Get the social media link of the specified user.\n" +
            "    Available: twitch, bnet, psn, xbox, steam, origin,\n" +
            "    youtube, twitter, facebook, switch, patreon, roblox\n" +
            " > mkthread\n" +
            "    Create a forum thread in the forum channel\n" +
            " > groupadd <(string)groupId> <userId>\n" +
            "    Add the specified user to the specified group\n" +
            " > groupkick <(string)groupId> <userId>\n" +
            "    Remove the specified user from the specified group\n" +
            " > roleadd <(int)roleId> <userId>\n" +
            "    Give the specified role to specified user\n" +
            " > rolekick <(int)roleId> <userId>\n" +
            "    Remove the specified role from specified user\n" +
            " > exit\n" +
            "    Log out and exit";
}
