package org.eu.xmon.petgramapi.web;

import static spark.Spark.*;
public class WebInitializer {

    /**
     * @apiNote create Web Server
     */
    public void initializeWebServer(){
        port(4571);
        staticFileLocation("/public");
    }
}
