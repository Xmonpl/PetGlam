package org.eu.xmon.petgramapi;

import org.eu.xmon.petgramapi.database.DbConnect;
import org.eu.xmon.petgramapi.web.WebInitializer;

/**
 * @Author Xmon
 */
public class Bootstrap {
    public static void main(String[] args) {
        final DbConnect dbConnect = new DbConnect();
        dbConnect.connect();

        final WebInitializer webInitializer = new WebInitializer();
        webInitializer.initializeWebServer();
    }
}
