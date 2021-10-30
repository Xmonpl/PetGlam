package org.eu.xmon.petgramapi.utils;

import spark.Request;

public class DebugUtils {
    public static void debugQueryParams(final Request request){
        request.queryParams().forEach(y ->{
            System.out.println(y + " -> " + request.queryParams(y));
        });
    }
}
