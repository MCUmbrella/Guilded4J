package vip.floatationdevice.g4j;

import javax.annotation.Nullable;

public class ChatMessage
{
    String id,type,channelId,content,createdAt,createdBy,createdByBotId,createdByWebhookId,updatedAt;

    public String getId(){return id;}
    public String getType(){return type;}
    public String getChannelId(){return channelId;}
    public String getContent(){return content;}
    public String getCreatedAt(){return createdAt;}
    public String getCreatedBy(){return createdBy;}
    @Nullable public String getCreatedByBotId(){return createdByBotId;}
    @Nullable public String getCreatedByWebhookId(){return createdByWebhookId;}
    @Nullable public String getUpdatedAt(){return updatedAt;}

    public ChatMessage setId(String id){this.id=id;return this;}
    public ChatMessage setType(String type){this.type=type;return this;}
    public ChatMessage setChannelId(String channelId){this.channelId=channelId;return this;}
    public ChatMessage setContent(String content){this.content=content;return this;}
    public ChatMessage setCreatedAt(String createdAt){this.createdAt=createdAt;return this;}
    public ChatMessage setCreatedBy(String createdBy){this.createdBy=createdBy;return this;}
    public ChatMessage setCreatedByBotId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ChatMessage setCreatedByWebhookId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}
    public ChatMessage setUpdatedAt(String updatedAt){this.updatedAt=updatedAt;return this;}

    public Boolean isSystemMessage(){return type==null || !type.equals("default");}
    public Boolean isBotMessage(){return createdByBotId!=null;}
    public Boolean isWebhookMessage(){return createdByWebhookId!=null;}
}
