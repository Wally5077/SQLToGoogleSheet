package models.jdbc.sql;

public class Clause {
    private final String key;
    private final Comparator comparator;
    private final Value value;
    public Clause(ParsingContext c) {
        key = c.nextName();
        comparator = Comparator.parse(c.nextName());
        value = new Value(c);
    }

    public enum Comparator {
        GREATER_THAN, GREATER_OR_EQUAL_THAN, LESS_THAN, LESS_OR_EQUAL_THAN, EQUAL;

        public static Comparator parse(String name) {
            return switch (name) {
                case ">=" -> GREATER_OR_EQUAL_THAN;
                case ">" -> GREATER_THAN;
                case "<=" -> LESS_OR_EQUAL_THAN;
                case "<" -> LESS_THAN;
                case "=" -> EQUAL;
                default -> throw new IllegalArgumentException("Illegal comparator '" + name + "'.");
            };
        }
    }

    public String getKey() {
        return key;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Value getValue() {
        return value;
    }
}
