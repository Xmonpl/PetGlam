package org.eu.xmon.petgramapi.utils;

import java.util.regex.Pattern;

public class RegexVariables {
    public static final Pattern USERNAME_REGEX = Pattern.compile("^[a-z0-9_-]{3,15}$", Pattern.MULTILINE);
    public static final Pattern EMAIL_REGEX = Pattern.compile("[^@ \t\r\n]+@[^@ \t\r\n]+\\.[^@ \t\r\n]+", Pattern.MULTILINE);
    public static final Pattern PHONE_REGEX = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
    public static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", Pattern.MULTILINE);
}
