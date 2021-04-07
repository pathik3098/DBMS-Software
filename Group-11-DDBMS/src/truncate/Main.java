package truncate;

import CreateOperation.Use;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String query = "TRUNCATE TABLE customer";
        Truncate truncate = new Truncate();
        Use db = new Use();
        String databaseName = db.getCurrentDB();
        truncate.executeTruncateQuery(query, "src//Database//newDb");
    }
}
