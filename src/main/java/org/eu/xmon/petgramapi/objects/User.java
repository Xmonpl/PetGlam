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
@Table(name = "users")
public class User {
    @Id
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Timestamp create_time;

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
