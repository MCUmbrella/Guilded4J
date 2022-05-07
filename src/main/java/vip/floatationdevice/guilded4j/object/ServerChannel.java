/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.Util;
import vip.floatationdevice.guilded4j.enums.ServerChannelType;

/**
 * Represents a server channel.
 * <a href="https://www.guilded.gg/docs/api/channels/ServerChannel" target=_blank>https://www.guilded.gg/docs/api/channels/ServerChannel</a>
 */
public class ServerChannel
{
    private String id, name, topic, createdAt, createdBy, updatedAt, serverId, parentId, groupId, archivedAt, archivedBy;
    private ServerChannelType type;
    private Integer categoryId;
    private Boolean isPublic;

    /**
     * Gets the ID of the channel.
     */
    public String getId(){return id;}

    /**
     * Gets the name of the channel.
     */
    public String getName(){return name;}

    /**
     * Gets the topic of the channel.
     */
    public String getTopic(){return topic;}

    /**
     * Gets the ISO 8601 timestamp that the channel was created at.
     */
    public String getCreationTime(){return createdAt;}

    /**
     * Gets the ID of the user who created this channel.
     */
    public String getCreator(){return createdBy;}

    /**
     * Gets the ISO 8601 timestamp that the channel was updated at.
     * @return The timestamp of the last update. Null if the channel has not been updated.
     */
    public String getUpdateTime(){return updatedAt;}

    /**
     * Gets the ID of the server that this channel is on.
     */
    public String getServerId(){return serverId;}

    /**
     * Gets the ID of the parent channel or parent thread, if present. Only relevant for server channels.
     */
    public String getParentId(){return parentId;}

    /**
     * Gets the ID of the group that this channel is in.
     */
    public String getGroupId(){return groupId;}

    /**
     * Gets the ISO 8601 timestamp that the channel was archived at, if relevant.
     * @return The timestamp of the last archive. Null if the channel has not been archived.
     */
    public String getArchiveTime(){return archivedAt;}

    /**
     * Gets the ID of the user who archived this channel.
     * @return The ID of the user who archived this channel. Null if the channel has not been archived.
     */
    public String getArchivedBy(){return archivedBy;}

    /**
     * Gets the type of the channel.
     * @return The ServerChannelType object representing the type of the channel.
     */
    public ServerChannelType getType(){return type;}

    /**
     * Gets the category ID of the channel.
     * @return The category ID of the channel. Null if the channel is not in a category.
     */
    public Integer getCategoryId(){return categoryId;}

    /**
     * Detects whether the channel can be accessed from users who are not member of the server.
     */
    public Boolean isPublic(){return isPublic != null && isPublic;}

    public ServerChannel setId(String id)
    {
        this.id = id;
        return this;
    }

    public ServerChannel setName(String name)
    {
        this.name = name;
        return this;
    }

    public ServerChannel setTopic(String topic)
    {
        this.topic = topic;
        return this;
    }

    public ServerChannel setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    public ServerChannel setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    public ServerChannel setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
        return this;
    }

    public ServerChannel setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    public ServerChannel setParentId(String parentId)
    {
        this.parentId = parentId;
        return this;
    }

    public ServerChannel setGroupId(String groupId)
    {
        this.groupId = groupId;
        return this;
    }

    public ServerChannel setArchivedAt(String archivedAt)
    {
        this.archivedAt = archivedAt;
        return this;
    }

    public ServerChannel setArchivedBy(String archivedBy)
    {
        this.archivedBy = archivedBy;
        return this;
    }

    public ServerChannel setType(ServerChannelType type)
    {
        this.type = type;
        return this;
    }

    public ServerChannel setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
        return this;
    }

    public ServerChannel setPublic(Boolean isPublic)
    {
        this.isPublic = isPublic;
        return this;
    }

    /**
     * Use the given JSON string to generate a ServerChannel object.
     * @param jsonString The JSON string to parse.
     * @return The generated ServerChannel object.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static ServerChannel fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("type"),
                    json.getStr("name"),
                    json.getStr("createdAt"),
                    json.getStr("createdBy"),
                    json.getStr("serverId"),
                    json.getStr("groupId")
            );
            return new ServerChannel()
                    .setId(json.getStr("id"))
                    .setName(json.getStr("name"))
                    .setTopic(json.getStr("topic"))
                    .setCreatedAt(json.getStr("createdAt"))
                    .setCreatedBy(json.getStr("createdBy"))
                    .setUpdatedAt(json.getStr("updatedAt"))
                    .setServerId(json.getStr("serverId"))
                    .setParentId(json.getStr("parentId"))
                    .setGroupId(json.getStr("groupId"))
                    .setArchivedAt(json.getStr("archivedAt"))
                    .setArchivedBy(json.getStr("archivedBy"))
                    .setType(ServerChannelType.fromString(json.getStr("type")))
                    .setCategoryId(json.getInt("categoryId"))
                    .setPublic(json.getBool("isPublic"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Converts the ServerChannel object to JSON string.
     * @return The JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("name", name)
                .set("topic", topic)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("updatedAt", updatedAt)
                .set("serverId", serverId)
                .set("parentId", parentId)
                .set("groupId", groupId)
                .set("archivedAt", archivedAt)
                .set("archivedBy", archivedBy)
                .set("type", type.toString().toLowerCase())
                .set("categoryId", categoryId)
                .set("isPublic", isPublic)
                .toString();
    }

}
