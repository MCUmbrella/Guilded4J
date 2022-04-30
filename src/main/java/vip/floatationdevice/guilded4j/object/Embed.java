/*
 Copyright (C) 2021 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.object;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Rich content sections associated with the message.
 * Properties with "webhook-markdown" support allow for the following: link, italic, bold, strikethrough, underline, inline code, block code, reaction, and mention.
 * This object is not part of the official Guilded API.
 */
public class Embed
{
    private String title, description, url, footerIconUrl, footerText = "", timestamp, thumbnailUrl, imageUrl, authorName, authorUrl, authorIconUrl;
    private int color = 0;
    private EmbedField[] fields;

    /**
     * Get the main header of the embed.
     */
    public String getTitle(){return title;}

    /**
     * Get the subtext of the embed.
     */
    public String getDescription(){return description;}

    /**
     * Get the URL to linkify the title field with.
     */
    public String getUrl(){return url;}

    /**
     * Get the URL of the small image in the footer.
     */
    public String getFooterIconUrl(){return footerIconUrl;}

    /**
     * Get the text in the small section at the bottom of the embed.
     */
    public String getFooterText(){return footerText;}

    /**
     * Get the ISO 8601 timestamp to put in the footer.
     */
    public String getTimestamp(){return timestamp;}

    /**
     * Get the URL of the image on the right of the embed's content.
     */
    public String getThumbnailUrl(){return thumbnailUrl;}

    /**
     * Get the URL of the main picture to associate with the embed.
     */
    public String getImageUrl(){return imageUrl;}

    /**
     * Get the embed author's name (above the title of the embed).
     */
    public String getAuthorName(){return authorName;}

    /**
     * Get the URL to linkify the author's name field with.
     */
    public String getAuthorNameUrl(){return authorUrl;}

    /**
     * Get the URL of the embed author's icon.
     */
    public String getAuthorIconUrl(){return authorIconUrl;}

    /**
     * Get the decimal value of the color that the left border should be.
     */
    public int getColor(){return color;}

    /**
     * Get the fields in the embed.
     */
    public EmbedField[] getFields(){return fields;}

    public Embed setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public Embed setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public Embed setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public Embed setFooterIconUrl(String footerIconUrl)
    {
        this.footerIconUrl = footerIconUrl;
        return this;
    }

    public Embed setFooterText(String footerText)
    {
        this.footerText = footerText;
        return this;
    }

    public Embed setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
        return this;
    }

    public Embed setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }

    public Embed setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
        return this;
    }

    public Embed setAuthorName(String authorName)
    {
        this.authorName = authorName;
        return this;
    }

    public Embed setAuthorUrl(String setAuthorUrl)
    {
        this.authorUrl = setAuthorUrl;
        return this;
    }

    public Embed setAuthorIconUrl(String authorIconUrl)
    {
        this.authorIconUrl = authorIconUrl;
        return this;
    }

    public Embed setColor(int color)
    {
        this.color = color;
        return this;
    }

    public Embed setFields(EmbedField[] fields)
    {
        this.fields = fields;
        return this;
    }

    /**
     * Use the given JSON string to generate an Embed object.
     * @param jsonString The JSON string to parse.
     * @return The generated Embed object.
     * @throws ClassCastException when the provided String's content isn't JSON format.
     */
    public static Embed fromString(String jsonString)
    {
        if(JSONUtil.isTypeJSON(jsonString))
        {
            JSONObject json = new JSONObject(jsonString);
            JSONArray fieldsArray = json.getJSONArray("fields");
            EmbedField[] fields = null;
            if(fieldsArray != null)
            {
                fields = new EmbedField[fieldsArray.size()];
                for(int i = 0; i != fieldsArray.size(); i++)
                    fields[i] = EmbedField.fromString(fieldsArray.get(i).toString());
            }
            return new Embed()
                    .setTitle(json.getStr("title"))
                    .setDescription(json.getStr("description"))
                    .setUrl(json.getStr("url"))
                    .setColor(json.getInt("color"))
                    .setTimestamp(json.getStr("timestamp"))
                    .setFooterText(json.getByPath("footer.text") != null ? json.getByPath("footer.text").toString() : null)
                    .setFooterIconUrl(json.getByPath("footer.icon_url") != null ? json.getByPath("footer.icon_url").toString() : null)
                    .setThumbnailUrl(json.getByPath("thumbnail.url") != null ? json.getByPath("thumbnail.url").toString() : null)
                    .setImageUrl(json.getByPath("image.url") != null ? json.getByPath("image.url").toString() : null)
                    .setAuthorName(json.getByPath("author.name") != null ? json.getByPath("author.name").toString() : null)
                    .setAuthorUrl(json.getByPath("author.url") != null ? json.getByPath("author.url").toString() : null)
                    .setAuthorIconUrl(json.getByPath("author.icon_url") != null ? json.getByPath("author.icon_url").toString() : null)
                    .setFields(fields);
        }
        else throw new ClassCastException("The provided String's content can't be converted to JSON object");
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
                .set("footer", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("text", footerText)
                        .set("icon_url", footerIconUrl))
                .set("thumbnail", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("url", thumbnailUrl))
                .set("image", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("url", imageUrl))
                .set("author", new JSONObject(new JSONConfig().setIgnoreNullValue(true))
                        .set("name", authorName)
                        .set("url", authorUrl)
                        .set("icon_url", authorIconUrl))
                .set("fields", new JSONArray(fields))
                .toString();
    }
}
