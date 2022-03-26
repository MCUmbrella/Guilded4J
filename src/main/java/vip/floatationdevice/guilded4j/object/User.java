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
 * Represents a user (or bot).
 * <a href="https://www.guilded.gg/docs/api/members/User" target=_blank>https://www.guilded.gg/docs/api/members/User</a>
 */
public class User
{
    private String id, type, name, createdAt;

    /**
     * Generate empty User object.
     */
    public User(){}

    /**
     * Get the ID of the user.
     * @return The ID of the user.
     */
    public String getId(){return id;}

    /**
     * Get the type of user.
     * @return The type of user. If this property is absent, it can assumed to be of type 'user'.
     */
    public String getType(){return type;}

    /**
     * Get the name of the user.
     * @return The real name of the user.
     */
    public String getName(){return name;}

    /**
     * Get the user's creation date.
     * @return The ISO 8601 timestamp that the user was created at.
     */
    public String getCreationTime(){return createdAt;}

    public User setId(String id)
    {
        this.id = id;
        return this;
    }

    public User setType(String type)
    {
        this.type = type;
        return this;
    }

    public User setName(String name)
    {
        this.name = name;
        return this;
    }

    public User setCreationTime(String createdAt)
    {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Generate a User object from the given JSON string.
     * @return User object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static User fromString(String jsonString)
    {
        if(JSONUtil.isJson(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("name"),
                    json.getStr("createdAt")
            );
            return new User()
                    .setId(json.getStr("id"))
                    .setType(json.getStr("type"))
                    .setName(json.getStr("name"))
                    .setCreationTime(json.getStr("createdAt"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the User object to a JSON string.
     * @return JSON string.
     */
    @Override public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("type", type)
                .set("name", name)
                .set("createdAt", createdAt)
                .toString();
    }
}
