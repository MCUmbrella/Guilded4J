package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChatMessage
{
    String id,type,channelId,content,createdAt,createdBy,createdByBotId,createdByWebhookId,updatedAt;
    // creation/update time's format looks like "2021-08-06T14:30:13.614Z" (UTC±0)
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

    public ChatMessage(String id,String channelId,String content,String createdAt,String createdBy)
    {
        this.setMsgId(id)
                .setChannelId(channelId)
                .setContent(content)
                .setCreationTime(createdAt)
                .setCreatorId(createdBy);
    }

    public ChatMessage(){}

    @Nullable public ChatMessage fromString(@Nonnull String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(json.getStr("id")==null||json.getStr("channelId")==null
        ||json.getStr("content")==null||json.getStr("createdAt")==null
        ||json.getStr("createdBy")==null)
            return null;
        this.setMsgId(json.getStr("id"))
            .setType(json.getStr("type"))
            .setChannelId(json.getStr("channelId"))
            .setContent(json.getStr("content"))
            .setCreationTime(json.getStr("createdAt"))
            .setCreatorId(json.getStr("createdBy"))
            .setBotCreatorId(json.getStr("createdByBotId"))
            .setWebhookCreatorId(json.getStr("createdByWebhookId"))
            .setUpdateTime(json.getStr("updatedAt"));
        return this;
    }
}
