package models.jdbc.sql;

import java.util.ArrayList;
import java.util.List;

public class Where {
    private final List<Clause> clauses = new ArrayList<>();

    public Where(ParsingContext c) {
        c.expectNextNameIgnoreCase("WHERE");

        parseNextClause(c);
    }

    private void parseNextClause(ParsingContext c) {
        clauses.add(new Clause(c));

        // TODO: 考慮 AND / OR
    }

    public List<Clause> getClauses() {
        return null;
    }
}
