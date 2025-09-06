package com.gundaero.alley.util;

import org.springframework.web.util.HtmlUtils;

public final class TextSanitizer {
    private TextSanitizer() {}

    public static String clean(String src) {
        if (src == null) return null;

        String s = src;

        s = HtmlUtils.htmlUnescape(s);
        s = s.replaceAll("(?is)<br\\s*/?>", "\n");
        s = s.replaceAll("(?is)</p\\s*>", "\n");
        s = s.replaceAll("(?is)<[^>]+>", " ");
        s = s.replace("\\n", "\n");
        s = s.replace("\uFEFF", "")
                .replace("\u200B", "")
                .replace("\u00A0", " ");
        s = s.replaceAll("(?m)^\\s*[◎※▲■◆▶▷→•·●◇]+\\s*", "");
        s = s.replaceAll("[ \\t\\x0B\\f\\r]+", " ");
        s = s.replaceAll("(\\R\\s*){3,}", "\n\n");
        s = s.replaceAll("\\s*\\n\\s*", "\n");
        return s.strip();
    }
}
