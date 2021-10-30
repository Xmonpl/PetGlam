package org.eu.xmon.petgramapi.database;

import com.dieselpoint.norm.Database;
import lombok.Getter;
import org.eu.xmon.petgramapi.objects.Post;
import org.eu.xmon.petgramapi.objects.User;

import java.io.File;
import java.io.IOException;

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
        /*
          @TODO Implement download database from dbhub.io
         */

        database.setJdbcUrl("jdbc:sqlite:database.db");
        if (!new File("database.db").exists()) {
            database.createTable(Post.class);
            database.createTable(User.class);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            /*
              @TODO implement upload database to dbhub.io
             */
        }));
    }
}
