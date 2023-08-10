/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.DocComment;

/**
 * Event fired when a doc comment gets deleted.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/DocCommentDeleted" target=_blank>https://www.guilded.gg/docs/api/websockets/DocCommentDeleted</a>
 */
public class DocCommentDeletedEvent extends GuildedEvent
{
    private final DocComment docComment;

    public DocCommentDeletedEvent(Object source, String json)
    {
        super(source, json);
        this.docComment = DocComment.fromJSON((JSONObject) new JSONObject(json).getByPath("d.docComment"));
    }

    /**
     * Get the comment that was deleted.
     * @return The comment's DocComment object.
     */
    public DocComment getDocComment(){return docComment;}
}
