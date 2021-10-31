package org.eu.xmon.petgramapi.web;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.eu.xmon.petgramapi.database.DbConnect;
import org.eu.xmon.petgramapi.objects.Post;
import org.eu.xmon.petgramapi.objects.User;
import org.eu.xmon.petgramapi.utils.PostImageUtils;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;

import static spark.Spark.*;
public class WebInitializer {

    public static String DOMAIN = "http://localhost";

    /**
     * @apiNote create Web Server
     */
    public void initializeWebServer(){
        port(80);
        staticFiles.location("/public");
        exception(Exception.class, (exception, req, res) -> exception.printStackTrace());

        // ########################## VIEW SECTION ########################################

        get("/accounts/signup", (request, response) -> {
            if (request.cookie("uuid") != null && request.cookie("token") != null ){
                final BCrypt.Result result = BCrypt.verifyer().verify((request.cookie("uuid") + "-" + request.ip()).toCharArray(), request.cookie("token"));
                if (result.verified){
                    response.redirect("/");
                    return null;
                }else{
                    final Map<String, Object> model = new HashMap<>();
                    return new VelocityTemplateEngine().render(
                            new ModelAndView(model, "private/register.html")
                    );
                }
            }else {
                final Map<String, Object> model = new HashMap<>();
                return new VelocityTemplateEngine().render(
                        new ModelAndView(model, "private/register.html")
                );
            }
        });

        get("/", (request, response) -> {
            if (request.cookie("uuid") != null && request.cookie("token") != null ){
                final BCrypt.Result result = BCrypt.verifyer().verify((request.cookie("uuid") + "-" + request.ip()).toCharArray(), request.cookie("token"));
                if (result.verified){
                    final User u = DbConnect.getDatabase().sql("SELECT * FROM users WHERE id = ?", request.cookie("uuid")).first(User.class);
                    if (u == null){
                        response.removeCookie("/", "token");
                        response.removeCookie("/", "uuid");
                        final Map<String, Object> model = new HashMap<>();
                        return new VelocityTemplateEngine().render(
                                new ModelAndView(model, "private/login.html")
                        );
                    }else {
                        final Map<String, Object> model = new HashMap<>();
                        model.put("user", u);
                        return new VelocityTemplateEngine().render(
                                new ModelAndView(model, "private/main.html")
                        );
                    }
                }else{
                    final Map<String, Object> model = new HashMap<>();
                    return new VelocityTemplateEngine().render(
                            new ModelAndView(model, "private/login.html")
                    );
                }
            }else {
                final Map<String, Object> model = new HashMap<>();
                return new VelocityTemplateEngine().render(
                        new ModelAndView(model, "private/register.html")
                );
            }
        });

        get("/accounts/signin", (request, response) -> {
            if (request.cookie("uuid") != null && request.cookie("token") != null ){
                final BCrypt.Result result = BCrypt.verifyer().verify((request.cookie("uuid") + "-" + request.ip()).toCharArray(), request.cookie("token"));
                if (result.verified){
                    response.redirect("/");
                    return null;
                }else{
                    final Map<String, Object> model = new HashMap<>();
                    return new VelocityTemplateEngine().render(
                            new ModelAndView(model, "private/login.html")
                    );
                }
            }else {
                final Map<String, Object> model = new HashMap<>();
                return new VelocityTemplateEngine().render(
                        new ModelAndView(model, "private/login.html")
                );
            }
        });

        // ########################## API SECTION #########################################

        /**
         * @Input emailOrPhoneOrUsername, password
         */
        post("/api/v1/user/login", (request, response) -> {
            final JSONObject jsonObject = new JSONObject();

            if (request.queryParams("emailOrPhoneOrUsername") == null || request.queryParams("password") == null) {
                jsonObject.put("success", false);
                jsonObject.put("error", "inputs is blanks!");
                return jsonObject.toString();
            }

            final User user = DbConnect.getDatabase().sql("SELECT * FROM users WHERE email_or_phone = ? OR username = ?", request.queryParams("emailOrPhoneOrUsername"), request.queryParams("emailOrPhoneOrUsername")).first(User.class);

            if (user == null){
                jsonObject.put("success", false);
                jsonObject.put("error", "incorrect login details!");
                return jsonObject.toString();
            }

            final BCrypt.Result result = BCrypt.verifyer().verify(request.queryParams("password").toCharArray(), user.getPassword());

            if (!result.verified){
                jsonObject.put("success", false);
                jsonObject.put("error", "incorrect password!");
                return jsonObject.toString();
            }

            jsonObject.put("success", true);
            jsonObject.put("uuid", user.id);
            jsonObject.put("username", user.getUsername());

            user.setLast_ip(request.ip());
            DbConnect.getDatabase().update(user);

            response.cookie("/", "token", BCrypt.withDefaults().hashToString(6, (user.id + "-" + request.ip()).toCharArray()), 3600,false, true);
            response.cookie("/", "uuid", user.id, 3600,false, true);

            System.out.println("[+-] SignIn User - " + user.toString());

            return jsonObject.toString();
        });

        get("/api/v1/user/logout", (request, response) -> {
            response.removeCookie("/", "token");
            response.removeCookie("/", "uuid");
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        });

        get("/cdn/:author/:post/:file", (request, response) -> {
            if (request.params("author") != null && request.params("post") != null && request.params("file") != null){
                final File file = new File("users/" + request.params("author") + "/" + request.params("post") + "/" + request.params("file"));
                if (file.exists()){
                    final HttpServletResponse raw = response.raw();
                    raw.getOutputStream().write(Files.readAllBytes(file.toPath()));
                    raw.getOutputStream().flush();
                    raw.getOutputStream().close();
                    response.status(200);
                    return raw;
                }else{
                    response.status(404);
                    return null;
                }
            }else {
                response.status(404);
                return null;
            }
        });

        /**
        * @Input file, description,
        */
        post("/api/v1/post/create", (request, response) -> {
            final JSONObject jsonObject = new JSONObject();

            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
            Part filePart = request.raw().getPart("file");

            if (request.cookie("uuid") != null && request.cookie("token") != null && filePart != null && request.queryParams("description") != null) {
                final BCrypt.Result result = BCrypt.verifyer().verify((request.cookie("uuid") + "-" + request.ip()).toCharArray(), request.cookie("token"));
                if (result.verified) {
                    var post = Post.builder()
                                    .post_id(UUID.randomUUID().toString())
                                    .author_id(request.cookie("uuid"))
                                    .create_time(new Timestamp(System.currentTimeMillis()).toString())
                                    .description(request.queryParams("description"))
                                    .build();
                    DbConnect.getDatabase().insert(post);
                    PostImageUtils.addImageToPost(filePart.getInputStream().readAllBytes(), post, filePart.getSubmittedFileName());
                    jsonObject.put("success", true);
                    jsonObject.put("post_id", post.post_id);
                    jsonObject.put("author_id", post.getAuthor_id());
                    return jsonObject.toString();
                }else{
                    response.removeCookie("/", "token");
                    response.removeCookie("/", "uuid");
                }
            }

            jsonObject.put("success", false);
            jsonObject.put("error", "incorrect token or file is empty!");
            return jsonObject.toString();
        });


        /**
         * @Input emailOrPhone, fullName, username, password
         */
        post("/api/v1/user/create", (request, response) -> {
            final JSONObject jsonObject = new JSONObject();

            if (request.queryParams("emailOrPhone") == null || request.queryParams("fullName") == null || request.queryParams("username") == null || request.queryParams("password") == null || request.queryParams("birthday") == null) {
                jsonObject.put("success", false);
                jsonObject.put("error", "inputs is blanks!");
                return jsonObject.toString();
            }

            if (DbConnect.getDatabase().sql("SELECT * from users WHERE email_or_phone = ? OR username = ?", request.queryParams("emailOrPhone"), request.queryParams("username")).first(User.class) != null){
                jsonObject.put("success", false);
                jsonObject.put("error", "Username or Email is already exist!");
                return jsonObject.toString();
            }

            UUID uuid = UUID.randomUUID();
            boolean use = true;
            do{
                if (DbConnect.getDatabase().sql("SELECT * from users WHERE id = ?", uuid.toString()).first(User.class) == null){
                    use = false;
                }else{
                    uuid = UUID.randomUUID();
                }
            }while (use);

            final User user = User.builder()
                    .id(uuid.toString())
                    .create_time(new Timestamp(System.currentTimeMillis()).toString())
                    .birthday(request.queryParams("birthday"))
                    .email_or_phone(request.queryParams("emailOrPhone"))
                    .full_name(request.queryParams("fullName"))
                    .username(request.queryParams("username"))
                    .last_ip(request.ip())
                    .posts("[]")
                    .biography("PetGram 😎🤙")
                    .sex("undefined")
                    .password(BCrypt.withDefaults().hashToString(12, request.queryParams("password").toCharArray()))
                    .build();

            DbConnect.getDatabase().insert(user);

            response.cookie("/", "token", BCrypt.withDefaults().hashToString(6, (user.id + "-" + request.ip()).toCharArray()), 3600,false, true);
            response.cookie("/", "uuid", user.id, 3600,false, true);

            jsonObject.put("success", true);
            jsonObject.put("uuid", user.id);
            jsonObject.put("username", user.getUsername());
            System.out.println("[+] New User - " + user.toString());
            return jsonObject.toString();
        });
    }
}
