package models.jdbc.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParsingContextTest {
    //In short: strip() is "Unicode-aware" evolution of trim(). Meaning trim() removes only characters <= U+0020 (space); strip() removes all Unicode whitespace characters (but not all control characters, such as \0)
    @Test
    void testExpectNextNameIgnoreCase() {
        var c = new ParsingContext("      A      Bb  CcC   Ddd");
        assertEquals("A", c.expectNextNameIgnoreCase("A"));
        assertEquals("Bb", c.expectNextNameIgnoreCase("BB"));
        assertEquals("CcC", c.expectNextNameIgnoreCase("ccc"));
        assertEquals("Ddd", c.expectNextNameIgnoreCase("DDD"));

        c.reset();
        assertThrows(IllegalStateException.class, () -> c.expectNextNameIgnoreCase("b"));
        assertThrows(IllegalStateException.class, () -> c.expectNextNameIgnoreCase("b"));
        assertThrows(IllegalStateException.class, () -> c.expectNextNameIgnoreCase("cccc"));
        assertThrows(IllegalStateException.class, () -> c.expectNextNameIgnoreCase("kkk"));

        c.reset();
        assertThrows(IllegalStateException.class, () -> c.expectNextNameIgnoreCase(" A "));
    }

    @Test
    void testUntilIgnoreCase() {
        var c = new ParsingContext(" A      Bb  CcC   Ddd, 1, 2, 3, 4, 5 PPP ( 1; 2; 3; 4; 5) END");
//        assertEquals("A", c.untilIgnoreCase("bb"));
//        assertEquals("Bb", c.untilIgnoreCase("CCC"));
//        assertEquals("CcC", c.untilIgnoreCase("ddd"));
        assertEquals("Ddd, 1, 2, 3, 4, 5", c.untilIgnoreCase("PpP"));
//        assertEquals("PPP ( 1; 2; 3; 4; 5)", c.untilIgnoreCase("END"));
    }

    @Test
    void testNextName() {
        var c = new ParsingContext(" A      Bb  CcC   Ddd ;     _ 23_ \n\n 0_0\n    abc");
        assertEquals("A", c.nextName());
        assertEquals("Bb", c.nextName());
        assertEquals("CcC", c.nextName());
        assertEquals("Ddd", c.nextName());
        assertEquals(";", c.nextName());
        assertEquals("_", c.nextName());
        assertEquals("23_", c.nextName());
        assertEquals("0_0", c.nextName());
        assertEquals("abc", c.nextName());
    }
}