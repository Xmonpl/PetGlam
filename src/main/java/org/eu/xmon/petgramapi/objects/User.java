package org.eu.xmon.petgramapi.objects;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    public String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email_or_phone")
    private String email_or_phone;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "last_ip")
    private String last_ip;

    @Column(name = "posts")
    private String posts;

    @Column(name = "biography")
    private String biography;

    @Column(name = "followers")
    private String followers;

    @Column(name = "following")
    private String following;

    @Column(name = "sex")
    private String sex;

}
