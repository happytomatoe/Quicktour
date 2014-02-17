package com.quicktour.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * @author Roman Lukash
 */
public class HTMLUtils {

    public static String cleanHTML(String toClean) {
        return Jsoup.clean(toClean, Whitelist.basic());
    }
}
