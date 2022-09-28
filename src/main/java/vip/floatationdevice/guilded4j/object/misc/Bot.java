/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;
import vip.floatationdevice.guilded4j.object.User;

/**
 * Represents a bot.
 * This class is not a part of the official Guilded API,
 * but is used to represent a bot in the Guilded4J library.
 */
public class Bot extends User
{
    private String createdBy, botId;

    /**
     * Generate a Bot object from a JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static Bot fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("name"),
                json.getStr("createdAt")
        );
        return (Bot) new Bot()
                .setCreator(json.getStr("createdBy"))
                .setBotId(json.getStr("botId"))
                // Bot functions end. The rest are inherited from User
                .setId(json.getStr("id"))
                .setName(json.getStr("name"))
                .setCreationTime(json.getStr("createdAt")); // convert it back to Bot
    }

    /**
     * Get the ID of the user who created this bot.
     */
    public String getCreator(){return createdBy;}

    public Bot setCreator(String createdBy)
    {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * Get the ID of this bot.
     * @return The bot's UUID.
     */
    public String getBotId(){return botId;}

    public Bot setBotId(String botId)
    {
        this.botId = botId;
        return this;
    }

    /**
     * Convert the Bot object to a JSON string.
     * @return The JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", getId())
                .set("name", getName())
                .set("createdAt", getCreationTime())
                .set("createdBy", getCreator())
                .set("botId", getBotId())
                .toString();
    }
}
