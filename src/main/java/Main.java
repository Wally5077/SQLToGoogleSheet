import lombok.SneakyThrows;
import models.contexts.Context;
import models.contexts.Repository;
import models.entities.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;

import static models.Gender.FEMALE;
import static models.Gender.MALE;
import static models.expressions.Select.select;
import static models.jdbc.PropertiesUtils.getProperties;

public class Main {

    public static void main(String[] args) throws Exception {
        googleSheetJDBC();
    }

    @SneakyThrows
    private static void googleSheetJDBC() {
        String googleSheetDriverClassName = "models.jdbc.GoogleSheetDriver";
        Class<?> clazz = Class.forName(googleSheetDriverClassName);
        // SELECT * FROM ${tableName} WHERE id = ?1
        String spreadSheetId = String.valueOf(getProperties("den.properties").get("google-sheet-id"));
        String spreadSheetName = "海賊職位表";
        String url = String.format("jdbc:googleSheets:%s/%s", spreadSheetId, spreadSheetName);

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ${tableName}")) {
            while (rs.next()) {
                System.out.println(rs.getString(1) + " - " + rs.getString(2));
            }
        }
    }

    public static void test() {
        Repository<User> repository = Context.createRepository(User.class);
        repository.save(new User("1", MALE));
        repository.save(new User("2", FEMALE));
        repository.save(new User("3", FEMALE));
        repository.save(new User("4", MALE));
        repository.save(new User("wally", MALE));
        var entities = select()
                .from(User.class)
                .where(user -> MALE == user.getGender())
                .query();
        entities.stream()
                .map(User::getName)
                .forEach(System.out::println);

        Arrays.stream(User.class.getDeclaredFields())
                .map(Field::getName)
                .forEach(System.out::println);
    }
}
