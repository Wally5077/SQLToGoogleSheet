import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlRegexTest {

    private static final String URL_REGEX = "^jdbc:googleSheets:[a-zA-Z\\d]+/[a-zA-Z\\d\\s]+$";

    // jdbc:googleSheets:${spreadSheetId}/${spreadSheetName}
    @Test
    public void testUrlRegex() {
        assertTrue("jdbc:googleSheets:spreadSheetId/spreadSheetName".matches(URL_REGEX));
        assertTrue("jdbc:googleSheets:spreadSheetId/spread sheet name".matches(URL_REGEX));
        assertTrue("jdbc:googleSheets:123/123".matches(URL_REGEX));
        assertFalse("java:googleSheets:spreadSheetId/spreadSheetName".matches(URL_REGEX));
        assertFalse("jdbc:googleSheets:/spreadSheetName".matches(URL_REGEX));
        assertFalse("jdbc:googleSheets:spreadSheetId/".matches(URL_REGEX));
    }

}
