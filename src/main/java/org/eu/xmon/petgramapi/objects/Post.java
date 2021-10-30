package org.eu.xmon.petgramapi.objects;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @Author Xmon
 */
@Data
@Table(name = "posts")
public class Post {
    @Id
    @Column(name = "post_id")
    private UUID post_id;

    @Column(name = "author_id")
    private UUID author_id;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "likes")
    private List<UUID> likes;

    @Column(name = "description")
    private String description;

    @Column(name = "comments")
    private List<String> comments;
}
