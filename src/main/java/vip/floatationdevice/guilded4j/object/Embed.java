/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.misc.EmbedField;

/**
 * Rich content sections associated with the message.<br>
 * <a href="https://www.guilded.gg/docs/api/chat/ChatEmbed" target=_blank>https://www.guilded.gg/docs/api/chat/ChatEmbed</a>
 */
public class Embed
{
    private String title, description, url, footerIconUrl, footerText, timestamp, thumbnailUrl, imageUrl, authorName, authorUrl, authorIconUrl;
    private Integer color;
    private EmbedField[] fields;

    /**
     * Use the given JSON object to generate an Embed object.
     */
    public static Embed fromJSON(JSONObject json)
    {
        JSONArray fieldsArray = json.getJSONArray("fields");
        EmbedField[] fields = null;
        if(fieldsArray != null)
        {
            fields = new EmbedField[fieldsArray.size()];
            for(int i = 0; i != fieldsArray.size(); i++)
                fields[i] = EmbedField.fromJSON(fieldsArray.getJSONObject(i));
        }
        return new Embed()
                .setTitle(json.getStr("title"))
                .setDescription(json.getStr("description"))
                .setUrl(json.getStr("url"))
                .setColor(json.getInt("color"))
                .setTimestamp(json.getStr("timestamp"))
                .setFooterText((String) json.getByPath("footer.text"))
                .setFooterIconUrl((String) json.getByPath("footer.icon_url"))
                .setThumbnailUrl((String) json.getByPath("thumbnail.url"))
                .setImageUrl((String) json.getByPath("image.url"))
                .setAuthorName((String) json.getByPath("author.name"))
                .setAuthorUrl((String) json.getByPath("author.url"))
                .setAuthorIconUrl((String) json.getByPath("author.icon_url"))
                .setFields(fields);
    }

    /**
     * Get the main header of the embed.
     */
    public String getTitle(){return title;}

    public Embed setTitle(String title)
    {
        this.title = title;
        return this;
    }

    /**
     * Get the subtext of the embed.
     */
    public String getDescription(){return description;}

    public Embed setDescription(String description)
    {
        this.description = description;
        return this;
    }

    /**
     * Get the URL to linkify the title field with.
     */
    public String getUrl(){return url;}

    public Embed setUrl(String url)
    {
        this.url = url;
        return this;
    }

    /**
     * Get the URL of the small image in the footer.
     */
    public String getFooterIconUrl(){return footerIconUrl;}

    public Embed setFooterIconUrl(String footerIconUrl)
    {
        this.footerIconUrl = footerIconUrl;
        return this;
    }

    /**
     * Get the text in the small section at the bottom of the embed.
     */
    public String getFooterText(){return footerText;}

    public Embed setFooterText(String footerText)
    {
        this.footerText = footerText;
        return this;
    }

    /**
     * Get the ISO 8601 timestamp to put in the footer.
     */
    public String getTimestamp(){return timestamp;}

    public Embed setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get the URL of the image on the right of the embed's content.
     */
    public String getThumbnailUrl(){return thumbnailUrl;}

    public Embed setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    /**
     * Get the URL of the main picture to associate with the embed.
     */
    public String getImageUrl(){return imageUrl;}

    public Embed setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Get the embed author's name (above the title of the embed).
     */
    public String getAuthorName(){return authorName;}

    public Embed setAuthorName(String authorName)
    {
        this.authorName = authorName;
        return this;
    }

    /**
     * Get the URL to linkify the author's name field with.
     */
    public String getAuthorNameUrl(){return authorUrl;}

    /**
     * Get the URL of the embed author's icon.
     */
    public String getAuthorIconUrl(){return authorIconUrl;}

    public Embed setAuthorIconUrl(String authorIconUrl)
    {
        this.authorIconUrl = authorIconUrl;
        return this;
    }

    /**
     * Get the decimal value of the color that the left border should be.
     */
    public int getColor(){return color;}

    /**
     * Set the color of the left border of the embed.
     * @param color Decimal value of the color that the left border should be (min: 0 or 0x000000, max: 16777215 or 0xffffff).
     */
    public Embed setColor(Integer color)
    {
        this.color = color;
        return this;
    }

    /**
     * Get the fields in the embed.
     */
    public EmbedField[] getFields(){return fields;}

    /**
     * Set the fields in the embed.
     * @param fields Table-like cells to add to the embed (max items 25).
     */
    public Embed setFields(EmbedField[] fields)
    {
        this.fields = fields;
        return this;
    }

    public Embed setAuthorUrl(String setAuthorUrl)
    {
        this.authorUrl = setAuthorUrl;
        return this;
    }

    /**
     * Converts the Embed object to JSON string.
     * @return The JSON string.
     */
    @Override
    public String toString()
    {
        return new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                .set("title", title)
                .set("description", description)
                .set("url", url)
                .set("color", color)
                .set("timestamp", timestamp)
                .set("footer", footerText == null ? null :
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("text", footerText)
                                .set("icon_url", footerIconUrl)
                )
                .set("thumbnail", thumbnailUrl == null ? null :
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("url", thumbnailUrl)
                )
                .set("image", imageUrl == null ? null :
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("url", imageUrl)
                )
                .set("author", authorName == null ? null :
                        new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                                .set("name", authorName)
                                .set("url", authorUrl)
                                .set("icon_url", authorIconUrl)
                )
                .set("fields", fields == null || fields.length == 0 ? null : new JSONArray(fields))
                .toString();
    }
}
