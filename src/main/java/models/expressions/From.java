package models.expressions;

import models.contexts.Repository;

import java.util.List;
import java.util.function.Predicate;

public class From<T> {

    private final Select select;
    private final Repository<T> table;

    private From(Select select, Repository<T> table) {
        this.select = select;
        this.table = table;
    }

    public static <T> From<T> from(Select select, Repository<T> table) {
        return new From<>(select, table);
    }

    public Where<T> where(Predicate<T> filter) {
        return Where.where(table, filter);
    }

    public List<T> query() {
        return table.findAll();
    }

}
