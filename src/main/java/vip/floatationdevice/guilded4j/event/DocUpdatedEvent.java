/*
 Copyright (C) 2021-2023 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.event;

import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.object.Doc;

/**
 * Event that is fired when a document is updated.<br>
 * <a href="https://www.guilded.gg/docs/api/websockets/DocUpdated" target=_blank>https://www.guilded.gg/docs/api/websockets/DocUpdated</a>
 */
public class DocUpdatedEvent extends GuildedEvent
{
    private final Doc doc;

    public DocUpdatedEvent(Object source, String json)
    {
        super(source, json);
        this.doc = Doc.fromJSON((JSONObject) new JSONObject(json).getByPath("d.doc"));
    }

    /**
     * Get the document that was updated.
     * @return The updated document's Doc object.
     */
    public Doc getDoc(){return doc;}
}
