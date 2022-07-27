package models.expressions;

import models.contexts.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.addAll;

public class Where<T> {

    private final Repository<T> table;

    private final List<Predicate<T>> filters;

    private Where(Repository<T> table, Predicate<T> filter) {
        this.table = table;
        addAll(filters = new ArrayList<>(), filter);
    }

    public static <T> Where<T> where(Repository<T> table, Predicate<T> filter) {
        return new Where<>(table, filter);
    }

    public Where<T> and(Predicate<T> filter) {
        filters.add(filter);
        return this;
    }

    public List<T> query() {
        var stream = table.findAll().stream();
        return filters.stream()
                .flatMap(stream::filter)
                .toList();
    }
}
