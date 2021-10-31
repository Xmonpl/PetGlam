package org.eu.xmon.petgramapi.objects;

import lombok.*;
import org.json.JSONArray;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Author Xmon
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @PrimaryKeyJoinColumn
    public String post_id;

    @Column(name = "author_id")
    private String author_id;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "likes")
    private String likes;

    @Column(name = "description")
    private String description;

    @Column(name = "comments")
    private String comments;

    @Column(name = "images")
    private String image_url;

}
