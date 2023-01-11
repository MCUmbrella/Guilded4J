/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.misc.GObjectQuery;
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
     */
    public Doc createDoc(String channelId, String title, String content)
    {
        return Doc.fromJSON(
                execute(Method.POST,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId),
                        new JSONObject().set("title", title).set("content", content)
                ).getJSONObject("doc")
        );
    }

    /**
     * Update a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocUpdate" target=_blank>https://www.guilded.gg/docs/api/docs/DocUpdate</a>
     * @param docId The id of the document to update.
     * @param title The new title of the document.
     * @param content The new content of the document.
     * @return The updated doc's Doc object.
     */
    public Doc updateDoc(String channelId, int docId, String title, String content)
    {
        return Doc.fromJSON(
                execute(Method.PUT,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId,
                        new JSONObject().set("title", title).set("content", content)
                ).getJSONObject("doc")
        );
    }

    /**
     * Delete a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocDelete" target=_blank>https://www.guilded.gg/docs/api/docs/DocDelete</a>
     * @param channelId The id of the channel the document is in.
     * @param docId The id of the document to delete.
     */
    public void deleteDoc(String channelId, int docId)
    {
        execute(Method.DELETE, DOC_CHANNEL_URL.replace("{channelId}", channelId) + "/" + docId, null);
    }

    /**
     * Get a document.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocRead" target=_blank>https://www.guilded.gg/docs/api/docs/DocRead</a>
     * @param docId The id of the document.
     * @return The doc's Doc object.
     */
    public Doc getDoc(String channelId, int docId)
    {
        return Doc.fromJSON(
                execute(Method.GET,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId).replace("{docId}", String.valueOf(docId)),
                        null
                ).getJSONObject("doc")
        );
    }

    /**
     * Get a list of the latest 25 docs from a channel.<br>
     * <a href="https://www.guilded.gg/docs/api/docs/DocReadMany" target=_blank>https://www.guilded.gg/docs/api/docs/DocReadMany</a>
     * @param channelId The id of the channel.
     * @return A list of the latest 25 docs from the channel.
     */
    public Doc[] getChannelDocs(String channelId)
    {
        JSONArray docsJson = execute(Method.GET, DOC_CHANNEL_URL.replace("{channelId}", channelId), null).getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i < docsJson.size(); i++) docs[i] = Doc.fromJSON(docsJson.getJSONObject(i));
        return docs;
    }

    /**
     * Get a list of docs using a query.
     * @param channelId The id of the channel.
     * @param query The query to search for.
     * @return Doc[]
     */
    public Doc[] getChannelDocs(String channelId, GObjectQuery query)
    {
        JSONArray docsJson = execute(Method.GET, DOC_CHANNEL_URL.replace("{channelId}", channelId) + query, null).getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i < docsJson.size(); i++) docs[i] = Doc.fromJSON(docsJson.getJSONObject(i));
        return docs;
    }
}
