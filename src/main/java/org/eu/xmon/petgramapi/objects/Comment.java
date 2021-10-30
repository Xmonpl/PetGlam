package org.eu.xmon.petgramapi.objects;

import lombok.Data;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

/**
 * @Author Xmon
 */
@Data
public class Comment {
    private UUID comment_id;
    private UUID post_id;
    private UUID author_id;
    private List<UUID> likes;
    private String comment;


    /**
     *
     * @return stringify object type
     */
    private String toJson(){
        return new JSONObject(this).toString();
    }
}
