package org.deslre.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinalUtil {

    public static final String LEVEL = "level";

    public static final String TITLE = "title";

    public static final List<String> ARROW;

    public static final Integer CODE = 200;

    public static final String[] SYMBOL = new String[]{"none", "arrow"};

    public static final String HIGHLIGHT_COLOR = "#EE4E6F";

    static {
        ARROW = new ArrayList<>(15);
        ARROW.add("名下");
        ARROW.add("转账");
    }
}
