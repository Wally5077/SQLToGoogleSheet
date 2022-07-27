package models.jdbc;

import java.io.IOException;

import static models.jdbc.PropertiesUtils.getProperties;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class DenGoogleSpreadsheet {
    private final GoogleSheetHelper googleSheet;

    public DenGoogleSpreadsheet() throws IOException {
        this.googleSheet = new GoogleSheetHelper(String.valueOf(getProperties("den.properties").get("google-sheet-id")),
                "Daily English Night");
    }
}
