/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;

/**
 * The basic item object in a 'list' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/listItems/ListItem" target=_blank>https://www.guilded.gg/docs/api/listItems/ListItem</a>
 */
public class ListItem //TODO: update
{
    class ListItemNote
    {
        private String createdAt, createdBy, content;

        public ListItemNote(String createdAt, String createdBy, String content)
        {
            this.createdAt = createdAt;
            this.createdBy = createdBy;
            this.content = content;
        }

        public String getCreationTime(){return createdAt;}

        public String getCreator(){return createdBy;}

        public String getContent(){return content;}
    }

    private String
            id, serverId, channelId, message, createdAt, createdBy, createdByBotId, createdByWebhookId,
            updatedAt, updatedBy, parentListItemId, completedAt, completedBy;
    private ListItemNote note;

    public String getId(){return id;}

    public String getChannelId(){return channelId;}

    public String getMessage(){return message;}

    public ListItemNote getNote(){return note;}

    public String getCreationTime(){return createdAt;}

    public String getCreatorId(){return createdBy;}

    public String getBotCreatorId(){return createdByBotId;}

    public String getWebhookCreatorId(){return createdByWebhookId;}

    public ListItem setId(String id)
    {
        this.id = id;
        return this;
    }

    public ListItem setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ListItem setChannelId(String channelId)
    {
        this.channelId = channelId;
        return this;
    }

    public ListItem setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ListItem setNote(ListItemNote note)
    {
        this.note = note;
        return this;
    }

    public ListItem setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ListItem setCreatorId(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ListItem setBotCreatorId(String createdByBotId)
    {
        this.createdByBotId = createdByBotId;
        return this;
    }

    public ListItem setWebhookCreatorId(String createdByWebhookId)
    {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Generate empty ListItem object - make sure to set all the essential fields before using it.
     */
    public ListItem(){}

    /**
     * Generate ListItem object from JSON string.
     * @param jsonString The JSON string.
     */
    public ListItem(String jsonString){fromString(jsonString);}

    /**
     * Use the given JSON string to generate ListItem object.
     * @param rawString The JSON string.
     * @return ListItem object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public ListItem fromString(String rawString)
    {
        if(JSONUtil.isJson(rawString))
        {
            JSONObject json = new JSONObject(rawString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("serverId"),
                    json.getStr("channelId"),
                    json.getStr("message"),
                    json.getStr("createdAt"),
                    json.getStr("createdBy")
            );
            return this.setId(json.getStr("id"))
                    .setServerId(json.getStr("serverId"))
                    .setChannelId(json.getStr("channelId"))
                    .setMessage(json.getStr("message"))
                    .setNote(new ListItemNote(
                            json.getByPath("note.createdAt").toString(),
                            json.getByPath("note.createdBy").toString(),
                            json.getByPath("note.content").toString()
                    ))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setBotCreatorId(json.getStr("createdByBotId"))
                    .setWebhookCreatorId(json.getStr("createdByWebhookId"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ListItem object to JSON string.
     * @return A JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("message", message)
                .set("note", note)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByBotId", createdByBotId)
                .set("createdByWebhookId", createdByWebhookId)
                .toString();
    }
}
