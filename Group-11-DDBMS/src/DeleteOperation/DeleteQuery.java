package DeleteOperation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CreateOperation.Use;
import SelectOperation.Object;
import consoleDriver.ConsoleRunner;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteQuery {

    Use db = new Use();
    String database = db.getCurrentDB();
    private static Logger log = LogManager.getLogger(DeleteQuery.class);
    public boolean executeQuery(String tableName, String conditionColumnName, String conditionColumnValue) throws IOException {

        Path filePath = Paths.get(database+"/" +tableName+".txt");
        Charset charset = StandardCharsets.UTF_8;
        if (checkTableName(database,tableName)) {
            log.info("Table "+tableName+" present in the database "+database);
            Map<String, Object> metaData = fetchMetaData(database,tableName);
            log.info("Fetching table "+tableName+" metadata");
            if (conditionColumnName != null) {
                log.info("query contain WHERE clause");
                log.info("Deleting records with "+conditionColumnValue+" value");
                try {
                    List<String[]> recordsAfterDelete = new ArrayList<>();
                    int ColumnPosition = metaData.get(conditionColumnName).getIndex();
                    List<String> records = Files.readAllLines(filePath,charset);
                    for (int i = 0; i<records.size();i++)
                    {
                        String[] eachRow = records.get(i).split("-");
                        if (eachRow[ColumnPosition].equalsIgnoreCase(conditionColumnValue)) {
                            System.out.println(records.get(i));
                        }
                        else {
                            recordsAfterDelete.add(eachRow);
                        }
                    }
                    log.info("Stored all the records after deleting the selected row");
                    Files.deleteIfExists(filePath);
                    Files.createFile(filePath);
                    for (String[] record : recordsAfterDelete) {
                        try {
                            String newData = "";
                            for (int i =0; i < record.length; i++) {
                                if (i == record.length-1) {
                                    newData += record[i].trim() + "\n";
                                }
                                else {
                                    newData += record[i].trim() + "-";
                                }
                            }
                            Files.writeString(filePath,newData, StandardOpenOption.APPEND);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info("Updated the table "+tableName);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                log.info("Query doesn't contain WHERE clause");
                log.info("Deleting all the records from the table "+tableName);
                Files.deleteIfExists(filePath);
                Files.createFile(filePath);
                return false;
            }
        }
        else {
            log.warn("Table "+tableName+" doesn't exist in database "+database);
            System.out.println("Table doesn't exist");
            return false;
        }
        return true;
    }

    public boolean checkTableName(String database, String tableName) {
        return Files.exists(Paths.get(database+"/" +tableName+".txt"));
    }

    public Map<String, Object> fetchMetaData(String database, String tableName){
        Path filePath = Paths.get(database+"/" +tableName+"meta.txt");
        Charset charset = StandardCharsets.UTF_8;
        Map<String, Object> metaData = new HashMap<>();
        try {
            List<String> dataValues = Files.readAllLines(filePath,charset);
            for (String data : dataValues) {
                String[] columnsData = data.split("-");
                int index = Integer.parseInt(columnsData[0]);
                String columnName = columnsData[1];
                String columnDataType = columnsData[2];
                Object obj = new Object(columnName,columnDataType,index);
                metaData.put(columnName,obj);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return metaData;
    }

}
