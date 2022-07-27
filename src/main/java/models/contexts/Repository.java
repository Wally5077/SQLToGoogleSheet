package models.contexts;

import java.util.*;

import static java.util.Optional.ofNullable;

public class Repository<Entity> {

    private final String tableName;
    private final Map<Object, Entity> entities;

    private Repository(String tableName) {
        this.tableName = tableName;
        this.entities = new HashMap<>();
    }

    public static <Entity> Repository<Entity> createRepository(Class<Entity> entity) {
        return new Repository<>(entity.getSimpleName());
    }

    public Optional<Entity> findById(Object id) {
        return ofNullable(entities.get(id));
    }

    public List<Entity> findAll() {
        return new ArrayList<>(entities.values());
    }

    public Entity save(Entity entity) {
        return entities.put(entities.size() + 1, entity);
    }

    public long count() {
        return entities.size();
    }
}
