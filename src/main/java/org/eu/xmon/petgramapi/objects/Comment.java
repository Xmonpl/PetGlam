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
    private String comment_id;
    private String post_id;
    private String author_id;
    private List<String> likes;
    private String comment;


    /**
     *
     * @return stringify object type
     */
    private String toJson(){
        return new JSONObject(this).toString();
    }
}
