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
 * The basic forum thread object in a 'forum' type channel.<br>
 * <a href="https://www.guilded.gg/docs/api/forums/ForumThread" target=_blank>https://www.guilded.gg/docs/api/forums/ForumThread</a>
 */
public class ForumThread {
    int id;
    String serverId, channelId, title, content, createdAt, createdBy, createdByBotId, createdByWebhookId;

    /**
     * Generate empty ForumThread object - make sure to set all the essential fields before using it.
     */
    public ForumThread() {
    }

    /**
     * Generate ForumThread object from JSON string.
     *
     * @param jsonString The JSON string.
     */
    public ForumThread(String jsonString) {
        fromString(jsonString);
    }

    /**
     * Get the thread's ID (it is not UUID).
     */
    public int getId() {
        return id;
    }

    public ForumThread setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Get the ID of the server to which the thread belongs.
     */
    public String getServerId() {
        return serverId;
    }

    public ForumThread setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    /**
     * Get the UUID of the channel to which the thread belongs.
     */
    public String getChannelId() {
        return channelId;
    }

    public ForumThread setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * Get the title of the thread.
     */
    public String getTitle() {
        return title;
    }//TODO: get an accurate answer on whether its optional or not

    public ForumThread setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get the content of the thread.
     */
    public String getContent() {
        return content;
    }//TODO: get an accurate answer on whether its optional or not

    public ForumThread setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp string that the forum thread was created at.
     */
    public String getCreationTime() {
        return createdAt;
    }

    public ForumThread setCreationTime(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * The ID of the user who created this forum thread.
     */
    public String getCreatorId() {
        return createdBy;
    }

    public ForumThread setCreatorId(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Broken because of the bot object migration.
     *
     * @return Always null.
     */
    @Deprecated
    public String getBotCreatorId() {
        return createdByBotId;
    }

    public ForumThread setBotCreatorId(String createdByBotId) {
        this.createdByBotId = createdByBotId;
        return this;
    }

    /**
     * Get the ID of the webhook who created this forum thread.
     *
     * @return A UUID string of the webhook who created the thread. If the creator isn't webhook, return {@code null}.
     */
    public String getWebhookCreatorId() {
        return createdByWebhookId;
    }

    public ForumThread setWebhookCreatorId(String createdByWebhookId) {
        this.createdByWebhookId = createdByWebhookId;
        return this;
    }

    /**
     * Use the given JSON string to generate ForumThread object.
     *
     * @param rawString The JSON string.
     * @return ForumThread object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException       when the provided String's content isn't JSON format.
     */
    public ForumThread fromString(String rawString) {
        if (JSONUtil.isJson(rawString)) {
            JSONObject json = new JSONObject(rawString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("serverId"),
                    json.getStr("channelId"),
                    json.getStr("createdAt"),
                    json.getStr("createdBy")
            );
            return this.setId(json.getInt("id"))
                    .setServerId(json.getStr("serverId"))
                    .setChannelId(json.getStr("channelId"))
                    .setTitle(json.getStr("title"))
                    .setContent(json.getStr("content"))
                    .setCreationTime(json.getStr("createdAt"))
                    .setCreatorId(json.getStr("createdBy"))
                    .setBotCreatorId(json.getStr("createdByBotId"))
                    .setWebhookCreatorId(json.getStr("createdByWebhookId"));
        } else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the ForumThread object to JSON string.
     *
     * @return A JSON string.
     */
    @Override
    public String toString() {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("serverId", serverId)
                .set("channelId", channelId)
                .set("title", title)
                .set("content", content)
                .set("createdAt", createdAt)
                .set("createdBy", createdBy)
                .set("createdByBotId", createdByBotId)
                .set("createdByWebhookId", createdByWebhookId)
                .toString();
    }
}
