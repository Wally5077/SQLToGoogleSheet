package models.jdbc;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GoogleSheetHelper {
    private final List<String> SCOPES = new ArrayList<>(SheetsScopes.all());
    protected static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    protected final GoogleCredentials googleCredentials = googleCredentials();
    private final String spreadSheetId;
    private final String sheetName;
    protected Sheets spreadSheet;

    public GoogleSheetHelper(String spreadSheetId, String spreadSheetName) {
        this.spreadSheetId = spreadSheetId;
        this.sheetName = spreadSheetName;
        initializeSheets();
    }

    protected void initializeSheets() {
        spreadSheet = sheets(sheetName);
    }

    @SneakyThrows
    private Sheets sheets(String sheetName) {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(googleCredentials))
                .setApplicationName(sheetName)
                .build();
    }

    protected GoogleCredentials googleCredentials() {
        try {
            InputStream in = GoogleSheetHelper.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
            }
            return GoogleCredentials.fromStream(in).createScoped(SCOPES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ValueRange lookUp(String range) throws IOException {
        return spreadSheet.spreadsheets().values().get(spreadSheetId, range).execute();
    }

    public UpdateValuesResponse update(String range, ValueRange valueRange) throws IOException {
        return spreadSheet.spreadsheets().values().update(spreadSheetId, range, valueRange)
                .setValueInputOption("RAW")
                .execute();
    }

}
