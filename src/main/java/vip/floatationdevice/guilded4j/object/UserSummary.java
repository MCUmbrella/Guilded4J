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
 * Represents a short summary of a user (or bot).
 * <a href="https://www.guilded.gg/docs/api/members/UserSummary" target=_blank>https://www.guilded.gg/docs/api/members/UserSummary</a>
 */
public class UserSummary
{
    String id, type, name;

    /**
     * Generate empty UserSummary object.
     */
    public UserSummary(){}

    /**
     * Get the ID of the user.
     * @return The ID of the user.
     */
    public String getId(){return id;}

    /**
     * Get the type of the user.
     * @return The type of user. If this property is absent, it can assumed to be of type 'user'.
     */
    public String getType(){return type;}

    /**
     * Get the name of the user.
     * @return The real name of the user.
     */
    public String getName(){return name;}

    public UserSummary setId(String id)
    {
        this.id = id;
        return this;
    }

    public UserSummary setType(String type)
    {
        this.type = type;
        return this;
    }

    public UserSummary setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Generate a UserSummary object from the given JSON string.
     * @return UserSummary object.
     * @throws IllegalArgumentException when the essential fields are not set.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static UserSummary fromString(String jsonString)
    {
        if(JSONUtil.isJson(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            Util.checkNullArgument(
                    json.getStr("id"),
                    json.getStr("name")
            );
            return new UserSummary()
                    .setId(json.getStr("id"))
                    .setType(json.getStr("type"))
                    .setName(json.getStr("name"));
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    /**
     * Convert the UserSummary object to a JSON string.
     * @return JSON string.
     */
    @Override public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("id", id)
                .set("type", type)
                .set("name", name)
                .toString();
    }
}
