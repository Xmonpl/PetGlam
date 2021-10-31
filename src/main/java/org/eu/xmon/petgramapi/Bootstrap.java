package org.eu.xmon.petgramapi;

import org.eu.xmon.petgramapi.database.DbConnect;
import org.eu.xmon.petgramapi.objects.Post;
import org.eu.xmon.petgramapi.utils.PostImageUtils;
import org.eu.xmon.petgramapi.web.WebInitializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

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
