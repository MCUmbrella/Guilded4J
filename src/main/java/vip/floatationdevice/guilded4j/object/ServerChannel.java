/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
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
     * Use the given JSON object to generate a ServerChannel object.
     * @throws IllegalArgumentException when the essential fields are missing.
     */
    public static ServerChannel fromJSON(JSONObject json)
    {
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

    /**
     * Gets the ID of the channel.
     */
    public String getId(){return id;}

    public ServerChannel setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Gets the name of the channel.
     */
    public String getName(){return name;}

    public ServerChannel setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Gets the topic of the channel.
     */
    public String getTopic(){return topic;}

    public ServerChannel setTopic(String topic)
    {
        this.topic = topic;
        return this;
    }

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

    public ServerChannel setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    /**
     * Gets the ID of the parent channel or parent thread, if present. Only relevant for server channels.
     */
    public String getParentId(){return parentId;}

    public ServerChannel setParentId(String parentId)
    {
        this.parentId = parentId;
        return this;
    }

    /**
     * Gets the ID of the group that this channel is in.
     */
    public String getGroupId(){return groupId;}

    public ServerChannel setGroupId(String groupId)
    {
        this.groupId = groupId;
        return this;
    }

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

    public ServerChannel setArchivedBy(String archivedBy)
    {
        this.archivedBy = archivedBy;
        return this;
    }

    /**
     * Gets the type of the channel.
     * @return The ServerChannelType object representing the type of the channel.
     */
    public ServerChannelType getType(){return type;}

    public ServerChannel setType(ServerChannelType type)
    {
        this.type = type;
        return this;
    }

    /**
     * Gets the category ID of the channel.
     * @return The category ID of the channel. Null if the channel is not in a category.
     */
    public Integer getCategoryId(){return categoryId;}

    public ServerChannel setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
        return this;
    }

    /**
     * Detects whether the channel can be accessed from users who are not member of the server.
     */
    public Boolean isPublic(){return isPublic != null && isPublic;}

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

    public ServerChannel setArchivedAt(String archivedAt)
    {
        this.archivedAt = archivedAt;
        return this;
    }

    public ServerChannel setPublic(Boolean isPublic)
    {
        this.isPublic = isPublic;
        return this;
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
