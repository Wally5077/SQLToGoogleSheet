package models.jdbc.sql;

import org.junit.jupiter.api.Test;

import static models.jdbc.sql.SqlQuery.query;
import static org.junit.jupiter.api.Assertions.*;

class SqlQueryTest {
    
    @Test
    void test() {
        // 雅婷逐字稿 紀錄你的垃圾話
        SqlQuery query = query("SELECT * FROM Users WHERE age = 16");
        Select select = query.getSelectStatement();
        assertTrue(select.isAllFields());
        
        assertEquals("Users", select.getTargetTable());
        
        Where where = select.getWhere();
        assertEquals(1, where.getClauses().size());
        
        Clause clause = where.getClauses().get(0);
        assertEquals("age", clause.getKey());
        Value value = clause.getValue();
        assertSame(16, value.getInteger());
    }

}