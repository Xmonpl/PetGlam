package org.eu.xmon.petgramapi.objects;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

/**
 * @Author Xmon
 */
@Data
@Builder
@ToString
public class Comment {
    private String comment_id;
    private String post_id;
    private String author_id;
    private String likes;
    private String comment;


    /**
     *
     * @return stringify object type
     */
    private String toJson(){
        return new JSONObject(this).toString();
    }
}
