package org.eu.xmon.petgramapi.objects;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class User {
    @Id
    private String id;

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
    private List<UUID> posts;

    @Column(name = "biography")
    private String biography;

    @Column(name = "followers")
    private List<UUID> followers;

    @Column(name = "following")
    private List<UUID> following;

    @Column(name = "sex")
    private String sex;

}
