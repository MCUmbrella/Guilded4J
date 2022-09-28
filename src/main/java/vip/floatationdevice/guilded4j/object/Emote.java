/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
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
    private String name, url;

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
                .setUrl(json.getStr("url"));
    }

    /**
     * Get the ID of the emote.
     */
    public int getId(){return id;}

    public Emote setId(int id)
    {
        this.id = id;
        return this;
    }

    /**
     * Get the name of the emote.
     */
    public String getName(){return name;}

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

    @Override
    public String toString()
    {
        return new JSONObject()
                .set("id", id)
                .set("name", name)
                .set("url", url)
                .toString();
    }
}
