package models.jdbc.sql;

public class Value {
    private final String text;
    private final Integer integer;

    // SQLToGoogleSheet

    public Value(ParsingContext c) {
        if (c.nextNameEqualsIgnoreCase("'")) /* text case */ {
            text = c.untilIgnoreCase("'");
            c.nextNameEqualsIgnoreCase("'"); // consume the right "'"
            integer = null;
        } else /* integer case */ {
            text = null;
            integer = Integer.parseInt(c.nextName());
        }

    }

    public String getText() {
        if (text == null) {
            throw new IllegalStateException(String.format("The value is an integer '%d', rather than a text.", integer));
        }
        return text;
    }

    public int getInteger() {
        if (integer == null) {
            throw new IllegalStateException(String.format("The value is a text '%s', rather than a number.", text));
        }
        return integer;
    }

}
