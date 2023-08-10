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
import vip.floatationdevice.guilded4j.object.DocComment;

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
                        DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId,
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
        execute(Method.DELETE,
                DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId,
                null
        );
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
        JSONArray docsJson = execute(Method.GET,
                DOC_CHANNEL_URL.replace("{channelId}", channelId),
                null
        ).getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i < docsJson.size(); i++)
            docs[i] = Doc.fromJSON(docsJson.getJSONObject(i));
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
        JSONArray docsJson = execute(Method.GET,
                DOC_CHANNEL_URL.replace("{channelId}", channelId) + query,
                null
        ).getJSONArray("docs");
        Doc[] docs = new Doc[docsJson.size()];
        for(int i = 0; i < docsJson.size(); i++)
            docs[i] = Doc.fromJSON(docsJson.getJSONObject(i));
        return docs;
    }

    /**
     * Create a comment on a doc.<br>
     * <a href="https://www.guilded.gg/docs/api/docComments/DocCommentCreate" target=_blank>https://www.guilded.gg/docs/api/docComments/DocCommentCreate</a>
     * @param channelId The UUID of the channel where the doc belongs to.
     * @param docId The ID of the doc to create the comment on.
     * @param content The content of the comment.
     * @return The newly created comment's DocComment object.
     */
    public DocComment createComment(String channelId, int docId, String content)
    {
        return DocComment.fromJSON(
                execute(Method.POST,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId + "/comments",
                        new JSONObject().set("content", content)
                ).getJSONObject("docComment")
        );
    }

    /**
     * Update a doc comment.<br>
     * <a href="https://www.guilded.gg/docs/api/docComments/DocCommentUpdate" target=_blank>https://www.guilded.gg/docs/api/docComments/DocCommentUpdate</a>
     * @param channelId The UUID of the channel where the doc belongs to.
     * @param docId The ID of the doc the comment belongs to.
     * @param docCommentId The ID of the comment to update.
     * @param content The new content of the comment.
     * @return The updated comment's DocComment object.
     */
    public DocComment updateComment(String channelId, int docId, int docCommentId, String content)
    {
        return DocComment.fromJSON(
                execute(Method.PATCH,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId + "/comments/" + docCommentId,
                        new JSONObject().set("content", content)
                ).getJSONObject("docComment")
        );
    }

    /**
     * Delete a comment on a doc.
     * <a href="https://www.guilded.gg/docs/api/docComments/DocCommentDelete" target=_blank>https://www.guilded.gg/docs/api/docComments/DocCommentDelete</a>
     * @param channelId The UUID of the channel where the doc belongs to.
     * @param docId The ID of the doc the comment belongs to.
     * @param docCommentId The ID of the comment to delete.
     */
    public void deleteComment(String channelId, int docId, int docCommentId)
    {
        execute(Method.DELETE,
                DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId + "/comments/" + docCommentId,
                null
        );
    }

    /**
     * Get a comment on a doc.
     * <a href="https://www.guilded.gg/docs/api/docComments/DocCommentRead" target=_blank>https://www.guilded.gg/docs/api/docComments/DocCommentRead</a>
     * @param channelId The UUID of the channel where the doc belongs to.
     * @param docId The ID of the doc the comment belongs to.
     * @param docCommentId The ID of the comment to get.
     * @return The comment's DocComment object.
     */
    public DocComment getComment(String channelId, int docId, int docCommentId)
    {
        return DocComment.fromJSON(
                execute(Method.GET,
                        DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId + "/comments/" + docCommentId,
                        null
                ).getJSONObject("docComment")
        );
    }

    /**
     * Get a list of comments on a doc.
     * <a href="https://www.guilded.gg/docs/api/docComments/DocCommentReadMany" target=_blank>https://www.guilded.gg/docs/api/docComments/DocCommentReadMany</a>
     * @param channelId The UUID of the channel where the doc belongs to.
     * @param docId The ID of the doc to get the comments of.
     * @return An array of the doc's comments.
     */
    public DocComment[] getComments(String channelId, int docId)
    {
        JSONArray commentsJson = execute(Method.GET,
                DOC_CHANNEL_URL.replace("{channelId}", channelId) + '/' + docId + "/comments",
                null
        ).getJSONArray("docComments");
        DocComment[] docComments = new DocComment[commentsJson.size()];
        for(int i = 0; i < commentsJson.size(); i++)
            docComments[i] = DocComment.fromJSON(commentsJson.getJSONObject(i));
        return docComments;
    }
}
