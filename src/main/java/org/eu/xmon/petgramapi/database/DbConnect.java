package org.eu.xmon.petgramapi.database;

import com.dieselpoint.norm.Database;
import lombok.Getter;
import org.eu.xmon.petgramapi.objects.Post;
import org.eu.xmon.petgramapi.objects.User;

/**
 * @Author Xmon
 */
public class DbConnect {

    @Getter
    private static final Database database = new Database();

    /**
     * @apiNote connect to database
     */
    public void connect(){
        database.setJdbcUrl("jdbc:sqlite:database.db");
        database.createTable(Post.class);
        database.createTable(User.class);
    }
}
