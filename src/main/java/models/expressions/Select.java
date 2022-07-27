package models.expressions;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNullElseGet;
import static models.contexts.Context.createRepository;

public class Select {

    private static final String DEFAULT_COLUMN = "*";

    private List<String> columns;

    private Select(String... columns) {
        this.columns = asList(columns);
    }

    public static Select select(String... columns) {
        return new Select(requireNonNullElseGet(columns, () -> new String[]{DEFAULT_COLUMN}));
    }

    public <T> From<T> from(String table) {
        try {
            //noinspection unchecked
            return from((Class<T>) Class.forName(table));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> From<T> from(Class<T> table) {
        return From.from(this, createRepository(table));
    }
}
