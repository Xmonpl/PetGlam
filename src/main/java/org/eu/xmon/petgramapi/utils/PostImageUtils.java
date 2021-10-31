package org.eu.xmon.petgramapi.utils;

import lombok.SneakyThrows;
import org.eu.xmon.petgramapi.database.DbConnect;
import org.eu.xmon.petgramapi.objects.Post;
import org.eu.xmon.petgramapi.objects.User;
import org.eu.xmon.petgramapi.web.WebInitializer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostImageUtils {
    @SneakyThrows
    public static String addImageToPost(final byte[] image, final Post post, final String filename){
        final File userProps = new File("users/" + post.getAuthor_id() + "/" + post.post_id + "/");
        if (!userProps.exists()) Files.createDirectories(userProps.toPath());

        final String extension = getExtensionByStringHandling(filename).get();

        final UUID uuid = UUID.randomUUID();

        post.setImage_url(WebInitializer.DOMAIN + "/cdn/" + post.getAuthor_id() + "/" + post.post_id + "/" + uuid + "." + extension);
        Files.write(new File(userProps.getAbsolutePath(), uuid.toString() + "." + extension).toPath(), image);

        System.out.println(post.getImage_url());

        DbConnect.getDatabase().update(post);

        final User user = DbConnect.getDatabase().sql("SELECT * FROM users WHERE id = ?", post.getAuthor_id()).first(User.class);
        final List<String> list;
        if (user.getPosts().equals("[]") || user.getPosts() == null){
            list = new ArrayList<>();
        }else {
            list = JsonUtils.fromJson(user.getPosts());
        }
        list.add(post.post_id);
        user.setPosts(JsonUtils.toJson(list));
        DbConnect.getDatabase().update(user);
        return post.getImage_url();
    }
    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
