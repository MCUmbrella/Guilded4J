/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Simple message object.<br>
 * <a href="https://www.guilded.gg/docs/api/chat/ChatMessage" target=_blank>https://www.guilded.gg/docs/api/chat/ChatMessage</a>
 * <p>A valid ChatMessage object contains 6 essential keys: id, type, channelId, content, createdAt, createdBy.</p>
 */
public class ChatMessage
{
    private String id,type,channelId,content,createdAt,createdBy,createdByBotId,createdByWebhookId,updatedAt;
    private Boolean isPrivate;
    private String[] replyMessageIds;

    /**
     * Get the message's UUID.
     * @return A UUID string that contains the UUID of the message.
     */
    public String getMsgId(){return id;}

    /**
     * Get the message's type.
     * @return "{@code system}" if the message is created by system(e.g. the "Channel created" message), else return "{@code default}".
     */
    public String getType(){return type;}

    /**
     * Get the UUID of the channel to which the message belongs.
     * @return A string that contains the channel's UUID of the message.
     */
    public String getChannelId(){return channelId;}

    /**
     * Get the content of the message.
     * @return A string contains the content of the message.
     */
    public String getContent(){return content;}

    /**
     * Get the creation time of the message.
     * @return A string that contains a time formatted like "2021-08-06T14:30:13.614Z" (UTC+-0)
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Get the ID of the message's creator (not UUID).
     * @return An 8-char-long string looks like "Ann6LewA", "8414gw5d" or some other familiar things. If the creator isn't a user, it will always be "Ann6LewA"
     */
    public String getCreatorId(){return createdBy;}

    /**
     * Get the UUID of the bot who created the message.
     * @return A UUID string of the bot who created the message. If the creator isn't bot, return {@code null}.
     */
    @Nullable public String getBotCreatorId(){return createdByBotId;}

    /**
     * Get the UUID of the webhook who created the message.
     * @return A UUID string of the webhook who created the message. If the creator isn't webhook, return {@code null}.
     */
    @Nullable public String getWebhookCreatorId(){return createdByWebhookId;}

    /**
     * Get the last update time of the message.
     * @return A string looks like the return value of getCreationTime(). If the message has never been changed, return {@code null}.
     */
    @Nullable public String getUpdateTime(){return updatedAt;}

    /**
     * Check if the message is a private reply.
     * @return {@code true} if the message is a private reply, {@code false} if not,
     * {@code null} if the message is not replying to any messages.
     */
    @Nullable public Boolean isPrivateReply(){return replyMessageIds!=null&&isPrivate;}

    /**
     * Get a list of UUIDs of the messages that this message replies to.
     * @return An array of UUID Strings, if the message isn't a reply, return {@code null}.
     */
    @Nullable public String[] getReplyMessageIds(){return replyMessageIds;}

    public ChatMessage setMsgId(String id){this.id=id;return this;}
    public ChatMessage setType(String type){this.type=type;return this;}
    public ChatMessage setChannelId(String channelId){this.channelId=channelId;return this;}
    public ChatMessage setContent(String content){this.content=content;return this;}
    public ChatMessage setCreationTime(String createdAt){this.createdAt=createdAt;return this;}
    public ChatMessage setCreatorId(String createdBy){this.createdBy=createdBy;return this;}
    public ChatMessage setBotCreatorId(String createdByBotId){this.createdByBotId=createdByBotId;return this;}
    public ChatMessage setWebhookCreatorId(String createdByWebhookId){this.createdByWebhookId=createdByWebhookId;return this;}
    public ChatMessage setUpdateTime(String updatedAt){this.updatedAt=updatedAt;return this;}
    public ChatMessage setPrivateReply(Boolean isPrivate){this.isPrivate=isPrivate;return this;}
    public ChatMessage setReplyMessageIds(String[] replyMessageIds){this.replyMessageIds=replyMessageIds;return this;}

    public Boolean isSystemMessage(){return type!=null&&type.equals("system");}
    public Boolean isBotMessage(){return createdByBotId!=null;}
    public Boolean isWebhookMessage(){return createdByWebhookId!=null;}

    /**
     * Generate ChatMessage object using 6 given essential keys.
     * @param id The UUID of the message.
     * @param type The type of the message.
     * @param channelId The UUID of the channel to which the message belongs.
     * @param content The message's content.
     * @param createdAt The creation date of the message (pay attention to the format).
     * @param createdBy The ID of the message's creator (this is not UUID).
     */
    public ChatMessage(String id,String type,String channelId,String content,String createdAt,String createdBy)
    {
        this.setMsgId(id)
                .setType(type)
                .setChannelId(channelId)
                .setContent(content)
                .setCreationTime(createdAt)
                .setCreatorId(createdBy);
    }

    /**
     * Generate empty ChatMessage object - don't call toString() or do any other operations before setting up the 6 essential keys.
     */
    public ChatMessage(){}

    /**
     * Use the given JSON string to generate ChatMessage object.
     * @return ChatMessage object.
     * @throws IllegalArgumentException when the string is missing at least 1 of the 6 essential keys.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public ChatMessage fromString(@Nonnull String rawString)
    {
        if(JSONUtil.isJson(rawString))
        {
            JSONObject json=new JSONObject(rawString);
            if(json.getStr("id")==null||json.getStr("type")==null
                    ||json.getStr("channelId")==null||json.getStr("content")==null
                    ||json.getStr("createdAt")==null||json.getStr("createdBy")==null)
                throw new IllegalArgumentException("At least 1 essential key of ChatMessage is missing");
            Object[] rawReplyMessageIds=json.containsKey("replyMessageIds")?json.getJSONArray("replyMessageIds").toArray():null;
            String[] replyMessageIds=rawReplyMessageIds!=null?new String[rawReplyMessageIds.length]:null;
            if(rawReplyMessageIds!=null) for(int i=0;i<rawReplyMessageIds.length;i++) replyMessageIds[i]=rawReplyMessageIds[i].toString();
            return this.setMsgId(json.getStr("id"))
                    .setType(json.getStr("type"))
                    .setChannelId(json.getStr("channelId"))
                    .setContent(json.getStr("content"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setBotCreatorId(json.getStr("createdByBotId"))
                    .setWebhookCreatorId(json.getStr("createdByWebhookId"))
                    .setUpdateTime(json.getStr("updatedAt"))
                    .setPrivateReply(json.getBool("isPrivate"))
                    .setReplyMessageIds(replyMessageIds);
        }else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ChatMessage object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id",id)
                .set("type",type)
                .set("channelId",channelId)
                .set("createdAt",createdAt)
                .set("createdBy",createdBy)
                .set("createdByBotId",createdByBotId)
                .set("createdByWebhookId",createdByWebhookId)
                .set("updatedAt",updatedAt)
                .set("content",content)
                .set("isPrivate", isPrivate)
                .set("replyMessageIds",replyMessageIds==null?null:new JSONArray(replyMessageIds))
                .toString();
    }
}
