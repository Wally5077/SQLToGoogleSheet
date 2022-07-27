package models.jdbc.sql;

public class Select {
    private final ParsingContext sqlQueryParsingContext;
    private final String[] fields;
    private final boolean allFields;
    private final String tableName;
    private final Where where;

    public Select(ParsingContext c) {
        sqlQueryParsingContext = c;
        c.expectNextNameIgnoreCase("SELECT"); // 下一個字串結點（不管大小寫），必須是 'SELECT'

        String name = c.untilIgnoreCase("FROM");
        allFields = "*".equals(name);

        if (!allFields) {
            name = name.replaceAll("([()])", ""); // 把左右括弧刪除
            fields = name.split("\\s*,\\s*");
        } else {
            fields = new String[]{};
        }

        c.expectNextNameIgnoreCase("FROM");
        tableName = c.nextName();

        where = new Where(c);
    }


    public boolean isAllFields() {
        return allFields;
    }

    public String getTargetTable() {
        return tableName;
    }

    public Where getWhere() {
        return where;
    }
}
