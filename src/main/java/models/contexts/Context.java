package models.contexts;

import java.util.HashMap;
import java.util.Map;

public final class Context {

    private static final Map<Class<?>, Repository<?>> TABLES = new HashMap<>();

    private Context() {
    }

    public static  <Entity> Repository<Entity> createRepository(Class<Entity> entity) {
        //noinspection unchecked
        return (Repository<Entity>) TABLES.computeIfAbsent(entity, Repository::createRepository);
    }
}
