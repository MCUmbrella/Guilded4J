/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a short summary of a user (or bot).<br>
 * <a href="https://www.guilded.gg/docs/api/members/UserSummary" target=_blank>https://www.guilded.gg/docs/api/members/UserSummary</a>
 */
public class UserSummary
{
    String id, type, name, avatar;

    /**
     * Generate a UserSummary object from the given JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static UserSummary fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("name")
        );
        return new UserSummary()
                .setId(json.getStr("id"))
                .setType(json.getStr("type"))
                .setName(json.getStr("name"))
                .setAvatar(json.getStr("avatar"));
    }

    /**
     * Get the ID of the user.
     * @return The ID of the user.
     */
    public String getId(){return id;}

    public UserSummary setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the type of the user.
     * @return The type of user. If this property is absent, it can assumed to be of type 'user'.
     */
    public String getType(){return type;}

    public UserSummary setType(String type)
    {
        this.type = type;
        return this;
    }

    /**
     * Get the name of the user.
     * @return The real name of the user.
     */
    public String getName(){return name;}

    public UserSummary setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Get the avatar image URL of the user.
     * @return URL string of the avatar image associated with the user.
     */
    public String getAvatar(){return avatar;}

    public UserSummary setAvatar(String avatar)
    {
        this.avatar = avatar;
        return this;
    }

    /**
     * Convert the UserSummary object to a JSON string.
     * @return JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("type", type)
                .set("name", name)
                .set("avatar", avatar)
                .toString();
    }
}
