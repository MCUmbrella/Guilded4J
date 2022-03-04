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
import java.util.regex.Pattern;

/**
 * A built-in CLI tool for testing and debugging Guilded4J library.
 */
@SuppressWarnings({"unused"})
public class G4JDebugger
{
    static Boolean dumpEnabled = false;

    static String parseMessage(ChatMessage m, Boolean prompt)
    {
        return prompt ? "\n[" + DateUtil.parse(m.getCreationTime()) + "] [" + m.getServerId() + "] [" + m.getChannelId() + "] (" + m.getMsgId() + ") <" + m.getCreatorId() + "> " + m.getContent() + prompt()
                : "\n[" + DateUtil.parse(m.getCreationTime()) + "] [" + m.getChannelId() + "] (" + m.getMsgId() + ") <" + m.getCreatorId() + "> " + m.getContent();
    }

    static String prompt(){return "\n[" + workdir + "] #";}

    static class GuildedEventListener
    {
        @Subscribe
        public void onInit(GuildedWebSocketInitializedEvent e)
        {
            System.out.print("\n[" + DateUtil.date() + "] [i] WebSocket client logged in (last message ID: " + e.getLastMessageId() + ", heartbeat: " + e.getHeartbeatInterval() + "ms)" + prompt());
            client.ws.setHeartbeatInterval(e.getHeartbeatInterval());
        }

        @Subscribe
        public void onDisconnect(GuildedWebSocketClosedEvent e)
        {
            System.out.print("\n[" + DateUtil.date() + "] [i] Connection closed " + (e.isRemote() ? "by remote peer (" : "(") + e.getCode() + ")\n    " + e.getReason() + prompt());
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
                System.out.print("\n[" + DateUtil.date() + "] [D] " + Arrays.toString(e.getUserIds()) + ": +" + e.getXpAmount() + " XP" + prompt());
        }

        @Subscribe
        public void onNicknameChange(TeamMemberUpdatedEvent e)
        {
            if(dumpEnabled)
                System.out.print("\n[" + DateUtil.date() + "] [D] " + e.getUserInfo().getUserId() + (e.getUserInfo().getNickname() == null ? ": nickname cleared" : ": nickname changed to '" + e.getUserInfo().getNickname() + "'") + prompt());
        }

        @Subscribe
        public void onRoleChange(TeamRolesUpdatedEvent e)
        {
            User[] users = e.getMembers();
            if(dumpEnabled)
            {
                System.out.println("\n[" + DateUtil.date() + "] [D] Member role changes:");
                for(User user : users)
                    System.out.println("    " + user.getUserId() + ": " + Arrays.toString(user.getRoleIds()));
                System.out.print(prompt());
            }
        }

        @Subscribe
        public void UnknownGuildedEvent(UnknownGuildedEvent e)
        {
            System.out.print("\n[" + DateUtil.date() + "] [!] Unknown event received: \n" + new JSONObject(e.getRawString()).toStringPretty() + prompt());
        }
    }

    static class G4JSession implements Serializable
    {
        public String savedToken = "";
        public String savedWorkdir = "";

        public Boolean save()
        {
            try
            {
                this.savedToken = client.authToken;
                this.savedWorkdir = workdir;
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("G4JSession.dat"));
                o.writeObject(this);
                o.close();
                return true;
            }
            catch(Throwable e)
            {
                System.err.println("[" + DateUtil.date() + "] [X] Failed to save session: " + e);
                return false;
            }
        }

        public Boolean restore()
        {
            try
            {
                ObjectInputStream i = new ObjectInputStream(new FileInputStream("G4JSession.dat"));
                G4JSession session = (G4JSession) i.readObject();
                this.savedToken = session.savedToken;
                this.savedWorkdir = session.savedWorkdir;
                i.close();
                return true;
            }
            catch(java.io.FileNotFoundException e) {return false;}
            catch(Throwable e)
            {
                System.err.println("[" + DateUtil.date() + "] [X] Failed to restore session: " + e);
                return false;
            }
        }
    }

    final static Scanner scanner = new Scanner(System.in);
    static String workdir = "(init)";
    static G4JClient client;

    public static void main(String[] args)
    {
        G4JSession session = new G4JSession();
        String token;
        if(session.restore())
        {
            client = new G4JClient(session.savedToken);
            workdir = session.savedWorkdir;
            System.out.println("[" + DateUtil.date() + "] [i] Restoring session");
        }
        else
        {
            System.out.print("Enter AuthToken: ");
            token = scanner.nextLine();
            client = new G4JClient(token);
            System.out.println("[" + DateUtil.date() + "] [i] Logging in");
        }
        client.ws.eventBus.register(new GuildedEventListener());
        client.ws.connect();
        String text = null;//typed command
        String textCache = "";//previous command
        for(; ; System.out.print(prompt()))
        {
            try
            {
                text = scanner.nextLine();
                if(text.equals("!!")) {text = textCache;}
                if(text.equals("save"))
                {
                    if(session.save()) {System.out.print("[" + DateUtil.date() + "] [i] G4JSession saved");}
                }
                else if(text.equals("test"))
                {
                    //
                }
                else if(text.equals("dump"))
                {
                    dumpEnabled = !dumpEnabled;
                    System.out.print("[" + DateUtil.date() + "] [i] Dump status: " + client.ws.toggleDump());
                }
                else if(text.startsWith("token ") && text.length() > 6)
                {
                    token = text.substring(6);
                    System.out.print("[" + DateUtil.date() + "] [i] Updated AuthToken");
                    client.setAuthToken(token);
                    client.ws.setAuthToken(token);
                }
                else if(text.equals("disconnect"))
                {
                    System.out.print("[" + DateUtil.date() + "] [i] Disconnecting");
                    client.ws.close();
                }
                else if(text.equals("pwd"))
                {
                    System.out.print("[" + DateUtil.date() + "] [i] Currently in channel: " + workdir);
                }
                else if(text.startsWith("cd ") && text.length() == 39)
                {
                    System.out.print("[" + DateUtil.date() + "] [i] Change target channel to " + text.substring(3));
                    workdir = text.substring(3);
                }
                else if(text.equals("cd"))
                {
                    System.out.print("[" + DateUtil.date() + "] [i] Clear target channel");
                    workdir = "(init)";
                }
                else if(text.equals("ls"))
                {
                    if(workdir.length() != 36) notifyCD();
                    else
                    {
                        ChatMessage[] msgs = client.getChannelMessages(workdir);
                        for(int i = msgs.length - 1; i >= 0; i--) System.out.print(parseMessage(msgs[i], false));
                    }
                }
                else if(text.startsWith("send ") && text.length() > 5)
                {
                    if(workdir.length() != 36) notifyCD();
                    else
                    {
                        String result = client.createChannelMessage(workdir, text.substring(5), null, null).toString();
                        if(dumpEnabled) System.out.print(RESULT_PFX + new JSONObject(result).toStringPretty());
                    }
                }
                else if(text.equals("reply"))
                {
                    if(workdir.length() != 36) notifyCD();
                    else
                    {
                        System.out.println("[i] UUID of the message(s) replying to: (1 UUID per line, empty line to end)");
                        ArrayList<String> uuids = new ArrayList<String>();
                        Scanner s = new Scanner(System.in);
                        String uuid;
                        boolean isPrivate;
                        for(; ; )
                        {
                            System.out.print("? ");
                            uuid = s.nextLine();
                            if(uuid.length() != 36) break;
                            else if(!uuids.contains(uuid)) uuids.add(uuid);
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
                        String result = client.createChannelMessage(workdir, s.nextLine(), uuidArray, isPrivate).toString();
                        if(dumpEnabled) System.out.print(RESULT_PFX + new JSONObject(result).toStringPretty());
                    }
                }
                else if(text.startsWith("rm ") && text.length() == 39)
                {
                    if(workdir.length() != 36) notifyCD();
                    else client.deleteChannelMessage(workdir, text.substring(3));
                }
                else if(text.startsWith("update ") && text.length() > 44)
                {
                    if(workdir.length() != 36) notifyCD();
                    else
                    {
                        ChatMessage result = client.updateChannelMessage(workdir, text.substring(7, 43), text.substring(44));
                        if(dumpEnabled)
                            System.out.print(RESULT_PFX + new JSONObject(result.toString()).toStringPretty());
                    }
                }
                else if(text.startsWith("get ") && text.length() == 40)
                {
                    System.out.print(new JSONObject(client.getMessage(workdir, text.substring(4)).toString()).toStringPretty());
                }
                else if(text.equals("mkitem"))
                {
                    if(workdir.length() != 36) notifyCD();
                    else
                    {
                        Scanner s = new Scanner(System.in);
                        String message, note;
                        System.out.print("[i] Enter the list item's display message:\n? ");
                        message = s.nextLine();
                        if(message.length() < 1)
                        {
                            System.err.println("[X] Message too short");
                            continue;
                        }
                        System.out.print("[i] Enter the note (optional):\n? ");
                        note = s.nextLine();
                        if(note.length() < 1) note = null;
                        String result = client.createListItem(workdir, message, note).toString();
                        if(dumpEnabled) System.out.print(RESULT_PFX + new JSONObject(result).toStringPretty());
                    }
                }
                else if(text.startsWith("addxp ") && text.length() > 15)
                {
                    String[] parsed = text.split(" ");
                    if(parsed.length == 3 && parsed[1].length() == 8 && Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                    {
                        int result = client.awardUserXp(parsed[1], Integer.parseInt(parsed[2]));
                        if(dumpEnabled) System.out.print(RESULT_PFX + result);
                    }
                    else
                        System.err.println("[" + DateUtil.date() + "] [X] Usage: addxp <(string)userId> <(int)amount>");
                }
                else if(text.startsWith("addrolexp ") && text.length() > 12)
                {
                    String[] parsed = text.split(" ");
                    if(parsed.length == 3 && Pattern.compile("[0-9]*").matcher(parsed[1]).matches() && Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                        client.awardRoleXp(Integer.parseInt(parsed[1]), Integer.parseInt(parsed[2]));
                    else
                        System.err.println("[" + DateUtil.date() + "] [X] Usage: addrolexp <(int)roleId> <(int)amount>");
                }
                else if(text.startsWith("react ") && text.length() > 8)
                {
                    if(workdir.length() != 36) notifyCD();
                    String[] parsed = text.split(" ");
                    if(parsed.length == 3 && Pattern.compile("[0-9]*").matcher(parsed[2]).matches())
                        client.createContentReaction(workdir, parsed[1], Integer.parseInt(parsed[2]));
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: react <contentId> <(int)emoteId>");
                }
                else if(text.startsWith("lsrole ") && text.length() == 15)
                {
                    System.out.print(Arrays.toString(client.getMemberRoles(text.substring(7))));
                }
                else if(text.startsWith("nick "))
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length < 3)
                    {
                        System.err.println("[" + DateUtil.date() + "] [X] Usage: nick <userID> <nickname>");
                    }
                    else
                    {
                        String nick = "";
                        for(int i = 2; i != arguments.length; i++) nick += (arguments[i] + " ");
                        String result = client.setMemberNickname(arguments[1], nick.trim());
                        if(dumpEnabled) System.out.print(RESULT_PFX + result);
                    }
                }
                else if(text.startsWith("rmnick ") && text.length() == 15)
                {
                    String result = client.setMemberNickname(text.substring(7), null);
                    if(dumpEnabled) System.out.print(RESULT_PFX + result);
                }
                else if(text.startsWith("smlink ") && text.length() > 15)
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length == 3)
                    {
                        HashMap<String, String> result = client.getSocialLink(arguments[1], SocialMedia.valueOf(arguments[2].toUpperCase()));
                        System.out.print(RESULT_PFX + result);
                    }
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: smlink <userID> <socialMediaName>");
                }
                else if(text.equals("mkthread"))
                {
                    if(workdir.length() != 36) notifyCD();
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
                    String result = client.createForumThread(workdir, title, content).toString();
                    if(dumpEnabled) System.out.print(RESULT_PFX + new JSONObject(result).toStringPretty());
                }
                else if(text.startsWith("groupadd"))
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length == 3) client.addGroupMember(arguments[1], arguments[2]);
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: groupadd <groupId> <userId>");
                }
                else if(text.startsWith("groupkick"))
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length == 3) client.removeGroupMember(arguments[1], arguments[2]);
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: groupkick <groupId> <userId>");
                }
                else if(text.startsWith("roleadd"))
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length == 3) client.addRoleMember(Integer.parseInt(arguments[1]), arguments[2]);
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: roleadd <roleId> <userId>");
                }
                else if(text.startsWith("rolekick"))
                {
                    String[] arguments = text.split(" ");
                    if(arguments.length == 3) client.removeRoleMember(Integer.parseInt(arguments[1]), arguments[2]);
                    else System.err.println("[" + DateUtil.date() + "] [X] Usage: rolekick <roleId> <userId>");
                }
                else if(text.equals("reconnect"))
                {
                    System.out.print("[i] Reconnecting");
                    if(client.ws != null) {client.ws.reconnect();}
                }
                else if(text.equals("exit"))
                {
                    System.out.println("[i] Exiting");
                    client.ws.close();
                    session.save();
                    break;
                }
                else if(text.equals("help")) System.out.print(helpText);
                else
                {
                    System.out.print("[" + DateUtil.date() + "] [!] Type 'help' to get available commands and usages");
                }
            }
            catch(NoSuchElementException e)
            {
                System.out.println("[" + DateUtil.date() + "] [i] Exiting");
                client.ws.close();
                session.save();
                break;
            }
            catch(GuildedException e)
            {
                System.err.println("[" + DateUtil.date() + "] [X] Operation failed\n    " + e.getCode() + ": " + e.getDescription());
            }
            catch(Exception e)
            {
                StringWriter esw = new StringWriter();
                e.printStackTrace(new PrintWriter(esw));
                System.err.println("\n[" + DateUtil.date() + "] [X] A Java runtime exception occurred while executing the command\n==========Begin stacktrace==========\n" + esw + "===========End stacktrace===========");
            }
            catch(Throwable e)
            {
                System.err.println("\n[" + DateUtil.date() + "] [X] A fatal error occurred. Program will exit");
                e.printStackTrace();
                System.exit(-1);
            }
            textCache = text;
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

    static void notifyCD(){System.err.println("[" + DateUtil.date() + "] [X] Specify a list channel UUID first");}

    final static String RESULT_PFX = "\n[" + DateUtil.date() + "] [D] Result:\n";
}
