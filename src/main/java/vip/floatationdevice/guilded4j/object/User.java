/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * Represents a user (or bot).<br>
 * <a href="https://www.guilded.gg/docs/api/members/User" target=_blank>https://www.guilded.gg/docs/api/members/User</a>
 */
public class User
{
    private String id, type, name, avatar, banner, createdAt;

    /**
     * Generate a User object from the given JSON object.
     * @throws IllegalArgumentException when the essential fields are not set.
     */
    public static User fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getStr("id"),
                json.getStr("name"),
                json.getStr("createdAt")
        );
        return new User()
                .setId(json.getStr("id"))
                .setType(json.getStr("type"))
                .setName(json.getStr("name"))
                .setAvatar(json.getStr("avatar"))
                .setBanner(json.getStr("banner"))
                .setCreationTime(json.getStr("createdAt"));
    }

    /**
     * Get the ID of the user.
     * @return The ID of the user.
     */
    public String getId(){return id;}

    public User setId(String id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the type of user.
     * @return The type of user. If this property is absent, it can assumed to be of type 'user'.
     */
    public String getType(){return type;}

    public User setType(String type)
    {
        this.type = type;
        return this;
    }

    /**
     * Get the name of the user.
     * @return The real name of the user.
     */
    public String getName(){return name;}

    public User setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Get the avatar image URL of the user.
     * @return URL string of the avatar image associated with the user.
     */
    public String getAvatar(){return avatar;}

    public User setAvatar(String avatar)
    {
        this.avatar = avatar;
        return this;
    }

    /**
     * Get the banner image URL of the user.
     * @return URL string of the banner image associated with the user.
     */
    public String getBanner(){return banner;}

    public User setBanner(String banner)
    {
        this.banner = banner;
        return this;
    }

    /**
     * Get the user's creation date.
     * @return The ISO 8601 timestamp that the user was created at.
     */
    public String getCreationTime(){return createdAt;}

    public User setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Convert the User object to a JSON string.
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
                .set("banner", banner)
                .set("createdAt", createdAt)
                .toString();
    }
}
