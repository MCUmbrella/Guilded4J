/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object.misc;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;

/**
 * Table-like cells to add to the embed.
 * This object is not part of the official Guilded API.
 */
public class EmbedField
{
    private String name, value = "";
    private boolean inline;

    public static EmbedField fromJSON(JSONObject json)
    {
        return new EmbedField()
                .setName(json.getStr("name"))
                .setValue(json.getStr("value"))
                .setInline(json.getBool("inline") != null ? json.getBool("inline") : false);
    }

    /**
     * Get the header of the table-like cell.
     */
    public String getName()
    {
        return name;
    }

    public EmbedField setName(String name)
    {
        this.name = name;
        return this;
    }

    /**
     * Get the subtext of the table-like cell.
     */
    public String getValue()
    {
        return value;
    }

    public EmbedField setValue(String value)
    {
        this.value = value;
        return this;
    }

    /**
     * Check if the field is inline.
     */
    public boolean isInline()
    {
        return inline;
    }

    public EmbedField setInline(boolean inline)
    {
        this.inline = inline;
        return this;
    }

    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("name", name)
                .set("value", value)
                .set("inline", inline)
                .toString();
    }
}
