package com.quicktour.utils;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

/**
 * @author Roman Lukash
 */
public class HTMLUtils {
    private static final TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/safehtml.xml");

    public static String cleanHTML(String toClean) {
        return processor.process(toClean);
    }
}
