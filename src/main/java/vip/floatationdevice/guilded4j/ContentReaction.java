package vip.floatationdevice.guilded4j;

import cn.hutool.json.JSONObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContentReaction// https://www.guilded.gg/docs/api/reactions/ContentReaction
{
    int id;
    String createdAt, createdBy, createdByBotId, createdByWebhookId;

    public int getId(){return id;}
    public String getCreationTime(){return createdAt;}
    public String getCreatorId(){return createdBy;}
    @Nullable public String getBotCreatorId(){return createdByBotId;}
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    public ContentReaction setId(int id){this.id=id;return this;}
    public ContentReaction setCreationTime(@Nonnull String createdAt){this.createdAt=createdAt;return this;}
    public ContentReaction setCreatorId(@Nonnull String createdBy){this.createdBy=createdBy;return this;}
    public ContentReaction setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ContentReaction setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}

    public ContentReaction(int id, String createdAt, String createdBy)
    {
        this.setId(id)
                .setCreationTime(createdAt)
                .setCreatorId(createdBy);
    }
    public ContentReaction(){}

    @Nullable public ContentReaction fromString(String rawString)
    {
        JSONObject json=new JSONObject(rawString);
        if(json.getStr("id")==null||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
            throw new IllegalArgumentException("At least 1 basic key of ContentReaction is missing");
        return this.setId(json.getInt("id"))
                .setCreationTime(json.getStr("createdAt"))
                .setCreatorId(json.getStr("createdBy"))
                .setBotCreatorId(json.getStr("createdByBotId"))
                .setWebhookCreatorId(json.getStr("createdByWebhookId"));

    }

    @Override
    public String toString()
    {
        StringBuilder s=new StringBuilder()
                .append("{\"id\":\"")
                .append(id)
                .append("\",\"createdAt\":\"")
                .append(createdAt)
                .append("\",");
        if(createdByBotId!=null) s.append("\"createdByBotId\":\"").append(createdByBotId).append("\",");
        if(createdByWebhookId!=null) s.append("\"createdByWebhookId\":\"").append(createdByWebhookId).append("\",");
        s.append("\"createdBy\":\"").append(createdBy).append("\"}");
        return s.toString();
    }
}
