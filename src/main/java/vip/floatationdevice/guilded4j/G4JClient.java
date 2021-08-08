package vip.floatationdevice.guilded4j;
import java.net.URI;
import java.util.ArrayList;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.common.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import vip.floatationdevice.guilded4j.event.*;

public class G4JClient extends WebSocketClient
{
    /*bruh moment*/static URI initURI(){try{return new URI("wss://api.guilded.gg/v1/websocket");}catch(Throwable e){System.out.println("\n[X] Failed to initialize Guilded bot URI!\n    How did that f**king happen? Anyway the program will exit now");System.exit(-1);return null;}}
    public static final URI WEBSOCKET_URI =initURI();
    public static final String MSG_CHANNEL_URL="https://www.guilded.gg/api/v1/channels/{channelId}/messages";
    public static String authToken="Bearer 0";
    public static EventBus bus = new EventBus();
    public G4JClient(String token)//initial function
    {
        super(WEBSOCKET_URI);
        this.setAuthToken(token);
    }
    @Override
    public void onOpen(ServerHandshake h)//when successfully logged in
    {
        System.out.println("\n[i] Connection opened");
    }
    @Override
    public void onMessage(String rawMessage)//when received a RAW String from Guilded server
    {
        JSONObject json=new JSONObject(rawMessage);
        //System.out.println(json.toStringPretty());
        String eventType=json.getStr("t");
        if(eventType!=null)
            if(eventType.equals("ChatMessageCreated"))
            {
                JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                bus.post(
                        new ChatMessageCreatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
                );
            }
            else if(eventType.equals("ChatMessageUpdated"))
            {
                JSONObject msgObj=(JSONObject)new JSONObject(rawMessage).getByPath("d.message");
                bus.post(
                        new ChatMessageCreatedEvent(this, new ChatMessage().fromString(msgObj.toString())).setOpCode(json.getInt("op"))
                );
            }
            else if(eventType.equals("ChatMessageDeleted"))
                bus.post(
                        new ChatMessageDeletedEvent(this)
                        .setDeletionTime((String)json.getByPath("d.message.deletedAt"))
                        .setMsgId((String)json.getByPath("d.message.id"))
                        .setChannelId((String)json.getByPath("d.message.channelId"))
                        .setOpCode(json.getInt("op"))
                );
            else if(eventType.equals("TeamXpAdded"))
            {
                ArrayList<String> userIds=new ArrayList<String>();
                JSONArray array=(JSONArray)new JSONObject(rawMessage).getByPath("d.userIds");
                Object[] converted=array.toArray();
                for(int i=0;i!=converted.length;i++) userIds.add((String)converted[i]);
                bus.post(
                        new TeamXpAddedEvent(this)
                                .setXpAmount((Integer) json.getByPath("d.amount"))
                                .setUserIds(userIds)
                                .setOpCode(json.getInt("op"))
                );
            }
            else bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setEventType(eventType).setRawString(rawMessage));
        else if(json.getInt("op")!=null)
            bus.post(new GuildedEvent(this).setOpCode(json.getInt("op")).setRawString(rawMessage));
        else bus.post(new GuildedEvent(this).setRawString(rawMessage));
    }

    public String createChannelMessage(String channelId, String msg)//send text message to specified channel
    {
        try
        {
            return HttpRequest.post(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body("{\"content\":\"$1\"}".replace("$1",msg)).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return "{\"Exception\":\""+e.toString()+"\"}";
        }
    }
    public String deleteChannelMessage(String channelId, String msgId)//delete a message from specified channel
    {
        try
        {
            return HttpRequest.delete(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return "{\"Exception\":\""+e.toString()+"\"}";
        }
    }
    public String updateChannelMessage(String channelId, String msgId, String content)
    {
        try
        {
            return HttpRequest.put(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    body("{\"content\":\"$1\"}".replace("$1",content)).
                    timeout(20000).execute().body();
        }catch (Exception e)
        {
            return "{\"Exception\":\""+e.toString()+"\"}";
        }
    }
    public String getMessage(String channelId, String msgId)//get a message from specified message UUID and channel UUID
    {
        try
        {
            return new JSONObject(
                    HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)+"/"+msgId).
                            header("Authorization","Bearer "+authToken).
                            header("Accept","application/json").
                            header("Content-type","application/json").
                            timeout(20000).execute().body()
            ).get("message").toString();
        }catch (Exception e)
        {
            return "{\"Exception\":\""+e.toString()+"\"}";
        }
    }
    public ArrayList<ChatMessage> getChannelMessages(String channelId)//get the last 50 msgs from specified channel
    {
        ArrayList<ChatMessage> messages=new ArrayList<ChatMessage>();
        try
        {
            String rawResult= HttpRequest.get(MSG_CHANNEL_URL.replace("{channelId}",channelId)).
                    header("Authorization","Bearer "+authToken).
                    header("Accept","application/json").
                    header("Content-type","application/json").
                    timeout(20000).execute().body();
            JSONArray array=new JSONObject(rawResult).getJSONArray("messages");
            Object[] converted=array.toArray();
            for(int i=0;i!=converted.length;i++) messages.add(new ChatMessage().fromString((new JSONObject(converted[i]).toString())));
            return messages;
        }catch (Exception e)
        {
            System.out.println("[X] Failed to get messages: "+e.toString());
            return messages;
        }
    }

    public void setAuthToken(String token)//to initialize or reset AuthToken
    {
        authToken=token;
        this.clearHeaders();
        this.addHeader("Authorization","Bearer "+authToken);
    }
    @Override
    public void onClose(int code, String reason, boolean remote)//when connection closed
    {
        System.out.println("\n[i] Connection closed " + (remote ? "by remote peer (" : "(") + code + ")\n    " + reason);
    }
    @Override
    public void onError(Exception e)
    {
        e.printStackTrace();
        System.exit(-1);
    }
}
