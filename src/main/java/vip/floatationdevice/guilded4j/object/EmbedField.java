/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Table-like cells to add to the embed.
 * This object is not part of the official Guilded API.
 */
public class EmbedField
{
    private String name, value = "";
    private boolean inline;

    /**
     * Get the header of the table-like cell.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the subtext of the table-like cell.
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Check if the field is inline.
     */
    public boolean isInline()
    {
        return inline;
    }

    public EmbedField setName(String name)
    {
        this.name = name;
        return this;
    }
    public EmbedField setValue(String value)
    {
        this.value = value;
        return this;
    }

    public EmbedField setInline(boolean inline)
    {
        this.inline = inline;
        return this;
    }

    public static EmbedField fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            return new EmbedField()
                    .setName(json.getStr("name"))
                    .setValue(json.getStr("value"))
                    .setInline(json.getBool("inline") != null ? json.getBool("inline") : false);
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
    }

    @Override public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("name", name)
                .set("value", value)
                .set("inline", inline)
                .toString();
    }
}
