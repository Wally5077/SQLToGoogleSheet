package models.jdbc;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.util.Properties;
import java.util.logging.Logger;

import static java.sql.DriverManager.registerDriver;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GoogleSheetDriver implements Driver {

    public static final Driver GOOGLE_SHEET_DRIVER = new GoogleSheetDriver();

    // jdbc:googleSheets:${spreadSheetId}/${spreadSheetName}
    private static final String URL_REGEX = "^jdbc:googleSheets:[a-zA-Z\\d]+/[a-zA-Z\\d\\s]+$";
    public static final int NUMBER_OF_PART_OF_URL = 3;
    public static final int NUMBER_OF_SPREAD_SHEET_INFO = 2;
    private static boolean hasRegistered;

    static {
        load();
    }

    @SneakyThrows
    public static void load() {
        if (!hasRegistered) {
            registerDriver(GOOGLE_SHEET_DRIVER);
            hasRegistered = true;
        }
    }

    @Override
    public Connection connect(String url, Properties info) {
        if (url.matches(URL_REGEX)) {
            var partOfUrl = url.split(":");
            if (NUMBER_OF_PART_OF_URL == partOfUrl.length) {
                String spreadSheetInfo = partOfUrl[2];
                var partOfSpreadSheetInfo = spreadSheetInfo.split("/");
                if (NUMBER_OF_SPREAD_SHEET_INFO == partOfSpreadSheetInfo.length) {
                    String spreadSheetId = partOfSpreadSheetInfo[0];
                    String spreadSheetName = partOfSpreadSheetInfo[1];
                    var googleSheet = new GoogleSheetHelper(spreadSheetId, spreadSheetName);
                    return new GoogleSheetConnection(googleSheet);
                }
            }
        }
        return null;
    }

    @Override
    public boolean acceptsURL(String url) {
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return true;
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }
}
