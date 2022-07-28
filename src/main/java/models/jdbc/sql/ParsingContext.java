package models.jdbc.sql;

import static java.lang.Character.*;
import static java.lang.String.format;
import static java.util.Arrays.stream;

public class ParsingContext {
    private final char[] content;
    private int pos = 0;

    public ParsingContext(String content) {
        this.content = content.toCharArray();
    }

    public String expectNextNameIgnoreCase(String expect) {
        String name = nextName();
        if (name.equalsIgnoreCase(expect)) {
            return name;
        }
        throw new IllegalStateException(format("Name Mismatch -- Expect %s, Actual: %s.", expect, name));
    }

    public boolean nextNameEqualsIgnoreCase(String... expects) {
        String next = nextName();
        return stream(expects).anyMatch(next::equalsIgnoreCase);
    }

    public String untilIgnoreCase(String until) {
        if (until.isBlank()) {
            throw new IllegalArgumentException("The until target name Must not be blank.");
        }
        consumeLeadingWhiteSpace();
        char firstUtilChar = toLowerCase(until.charAt(0));
        int untilLength = until.length();
        int anchorIndex = 0;
        while (pos < content.length) {
            char scanningChar = content[pos];
            if (toLowerCase(scanningChar) == firstUtilChar) {
                String nextName = new String(content, pos, untilLength);
                if (until.equalsIgnoreCase(nextName)) {
                    return new String(content, anchorIndex, pos - anchorIndex).strip();
                }
                pos += untilLength;
            } else if (isWhitespace(content[anchorIndex]) && isAlphabetic(scanningChar)) {
                // todo
                anchorIndex = pos - 1;
            }
            pos++;
        }
        throw new IllegalStateException(format("Name Mismatch -- Expect %s.", until));
    }

    public String nextName() {
        consumeLeadingWhiteSpace();
        StringBuilder nameBuilder = new StringBuilder();
        while (pos < content.length) {
            char nameChar = content[pos++];
            if (!isWhitespace(nameChar)) {
                nameBuilder.append(nameChar);
            } else {
                break;
            }
        }
        return nameBuilder.toString();
    }

    private void consumeLeadingWhiteSpace() {
        while (pos < content.length) {
            if (isWhitespace(content[pos])) {
                pos++;
            } else {
                break;
            }
        }
    }

    public void reset() {
        pos = 0;
    }
}
