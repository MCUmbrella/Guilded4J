package vip.floatationdevice.g4j;

import cn.hutool.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChatMessage
{
    String id,type,channelId,content,createdAt,createdBy,createdByBotId,createdByWebhookId,updatedAt;
    // creation/update time's format looks like "2021-08-06T14:30:13.614Z"
    //user ID is not user's display name or a UUID string - it's something different. looks like "Ann6LewA", "8412gw5d"

    public String getMsgId(){return id;}
    public String getType(){return type;}
    public String getChannelId(){return channelId;}
    public String getContent(){return content;}
    public String getCreationTime(){return createdAt;}
    public String getCreatorId(){return createdBy;}
    @Nullable public String getBotCreatorId(){return createdByBotId;}
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}
    @Nullable public String getUpdateTime(){return updatedAt;}

    public ChatMessage setMsgId(String id){this.id=id;return this;}
    public ChatMessage setType(String type){this.type=type;return this;}
    public ChatMessage setChannelId(String channelId){this.channelId=channelId;return this;}
    public ChatMessage setContent(String content){this.content=content;return this;}
    public ChatMessage setCreationTime(String createdAt){this.createdAt=createdAt;return this;}
    public ChatMessage setCreatorId(String createdBy){this.createdBy=createdBy;return this;}
    public ChatMessage setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ChatMessage setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}
    public ChatMessage setUpdateTime(String updatedAt){this.updatedAt=updatedAt;return this;}

    public Boolean isSystemMessage(){return type!=null && !type.equals("default");}
    public Boolean isBotMessage(){return createdByBotId!=null;}
    public Boolean isWebhookMessage(){return createdByWebhookId!=null;}

    @Nullable public ChatMessage fromString(@Nonnull String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(json.getInt("op")==null||json.getByPath("d.message.id")==null||json.getByPath("d.message.type")==null
        ||json.getByPath("d.message.channelId")==null||json.getByPath("d.message.content")==null
        ||json.getByPath("d.message.createdAt")==null||json.getByPath("d.message.createdBy")==null)
            return null;
        this.setMsgId((String)json.getByPath("d.message.id"))
            .setType((String)json.getByPath("d.message.type"))
            .setChannelId((String)json.getByPath("d.message.channelId"))
            .setContent((String)json.getByPath("d.message.content"))
            .setCreationTime((String)json.getByPath("d.message.createdAt"))
            .setCreatorId((String)json.getByPath("d.message.createdBy"))
            .setBotCreatorId((String)json.getByPath("d.message.createdByBotId"))
            .setWebhookCreatorId((String)json.getByPath("d.message.createdByWebhookId"))
            .setUpdateTime((String)json.getByPath("d.message.updatedAt"));
        return this;
    }
}
