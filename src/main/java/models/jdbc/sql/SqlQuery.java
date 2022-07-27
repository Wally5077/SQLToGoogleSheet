package models.jdbc.sql;

public class SqlQuery {
    private final ParsingContext sqlQueryParsingContext;
    private final Select select;

    public SqlQuery(ParsingContext c) {
        sqlQueryParsingContext = c;
        select = new Select(c);
    }


    public static SqlQuery query(String sql) {
        return new SqlQuery(new ParsingContext(sql));
    }

    public Select getSelectStatement() {
        return select;
    }
}
