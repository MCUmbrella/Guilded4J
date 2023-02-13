/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.Util;

/**
 * The emote object.<br>
 * <a href="https://www.guilded.gg/docs/api/emote/Emote" target=_blank>https://www.guilded.gg/docs/api/emote/Emote</a>
 */
public class Emote
{
    private int id;
    private String name, url, serverId;

    public static Emote fromJSON(JSONObject json)
    {
        Util.checkNullArgument(
                json.getInt("id"),
                json.getStr("name"),
                json.getStr("url")
        );
        return new Emote()
                .setId(json.getInt("id"))
                .setName(json.getStr("name"))
                .setUrl(json.getStr("url"))
                .setServerId(json.getStr("serverId"));
    }

    /**
     * Get the ID of the emote.
     */
    public int getId(){return id;}

    /**
     * Get the name of the emote.
     */
    public String getName(){return name;}

    /**
     * Get the URL of the emote image.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Get the ID of the server the emote was created on.
     */
    public String getServerId()
    {
        return serverId;
    }

    public Emote setId(int id)
    {
        this.id = id;
        return this;
    }

    public Emote setName(String name)
    {
        this.name = name;
        return this;
    }

    public Emote setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public Emote setServerId(String serverId)
    {
        this.serverId = serverId;
        return this;
    }

    @Override
    public String toString()
    {
        return new JSONObject()
                .set("id", id)
                .set("name", name)
                .set("url", url)
                .set("serverId", serverId)
                .toString();
    }
}
