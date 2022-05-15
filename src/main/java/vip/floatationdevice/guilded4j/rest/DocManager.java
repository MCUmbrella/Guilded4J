/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import vip.floatationdevice.guilded4j.exception.GuildedException;
import vip.floatationdevice.guilded4j.object.Doc;

import static vip.floatationdevice.guilded4j.G4JClient.DOC_CHANNEL_URL;

/**
 * Manages the documents.
 */
public class DocManager extends RestManager
{
    public DocManager(String authToken)
    {
        super(authToken);
    }

    /**
     * Create a new document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocCreate" target=_blank>https://www.guilded.gg/docs/api/docs/DocCreate</a>
     * @param title The title of the document.
     * @param content The content of the document.
     * @return The newly created doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc createDoc(String channelId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.post(DOC_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Update a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocUpdate" target=_blank>https://www.guilded.gg/docs/api/docs/DocUpdate</a>
     * @param docId The id of the document to update.
     * @param title The new title of the document.
     * @param content The new content of the document.
     * @return The updated doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc updateDoc(String channelId, int docId, String title, String content)
    {
        JSONObject result = new JSONObject(HttpRequest.put(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body(new JSONObject().set("title", title).set("content", content).toString()).
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Delete a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocDelete" target=_blank>https://www.guilded.gg/docs/api/docs/DocDelete</a>
     * @param channelId The id of the channel the document is in.
     * @param docId The id of the document to delete.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public void deleteDoc(String channelId, int docId)
    {
        String result = HttpRequest.delete(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body();
        if(JSONUtil.isTypeJSON(result))
        {
            JSONObject json = new JSONObject(result);
            if(json.containsKey("code")) throw new GuildedException(json.getStr("code"), json.getStr("message"));
            else throw new ClassCastException("DocDelete returned an unexpected JSON string");
        }
    }

    /**
     * Get a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocRead" target=_blank>https://www.guilded.gg/docs/api/docs/DocRead</a>
     * @param docId The id of the document.
     * @return The doc's Doc object.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc getDoc(String channelId, int docId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        return Doc.fromString(result.get("doc").toString());
    }

    /**
     * Get a list of the latest 50 docs from a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocReadMany" target=_blank>https://www.guilded.gg/docs/api/docs/DocReadMany</a>
     * @param channelId The id of the channel.
     * @return A list of the latest 50 docs from the channel.
     * @throws GuildedException if Guilded API returned an error JSON string.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public Doc[] getChannelDocs(String channelId)
    {
        JSONObject result = new JSONObject(HttpRequest.get(DOC_CHANNEL_URL.replace("{channelId}", channelId)).
                header("Authorization", "Bearer " + authToken).
                header("Accept", "application/json").
                header("Content-type", "application/json").
                timeout(httpTimeout).execute().body());
        if(result.containsKey("code")) throw new GuildedException(result.getStr("code"), result.getStr("message"));
        System.out.println(result);
        JSONArray docsJson = result.getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i != docsJson.size(); i++)
            docs[i] = Doc.fromString(docsJson.get(i).toString());
        return docs;
    }
}
