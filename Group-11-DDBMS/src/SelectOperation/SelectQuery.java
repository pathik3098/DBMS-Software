package SelectOperation;

import CreateOperation.Use;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQuery {

    Use database = new Use();
    String databaseName = database.getCurrentDB();
    private static Logger log = LogManager.getLogger(SelectQuery.class);

    public List<String> executeQuery(String tableName, String[] requiredColumns, String conditionColumnName, String conditionColumnValue) throws IOException {
        List<String> allRecords = new ArrayList<>();
        List<String> requiredRecords = new ArrayList<>();
        List<String> requiredColumnRecords = new ArrayList<>();
        if (checkTableName(databaseName,tableName)) {
            log.info("Table "+tableName+" present in the database "+database);
            String[] eachRow;
            Map<String, Object> metaData = getTableMetaData(databaseName,tableName);
            log.info("Fetching table "+tableName+" metadata");
            String[] columns = requiredColumns;
            if (columns.length == 0) {
                log.info("query doesn't contain WHERE clause");
                log.info("Fetching all table "+tableName+" records");
                allRecords = Files.readAllLines(Paths.get(databaseName + "/" + tableName + ".txt"));
                log.info("Returned all the table "+tableName+" records");
                return allRecords;
            }
            else {
                if (conditionColumnName != null) {
                    log.info("Query has a WHERE clause-- fetching records from conditional clause");
                    try {
                        allRecords = Files.readAllLines(Paths.get( databaseName + "/" + tableName+".txt"));
                        for (String record : allRecords) {
                            eachRow = record.split("-");
                            for (int i = 0; i < eachRow.length;i++) {
                                if (eachRow[i].equals(conditionColumnValue)) {
                                    requiredRecords.add(record);
                                }
                            }
                        }
                        log.info("Added selected records to data structure");
                    }
                    catch (IOException e) {
                        log.warn("EXCEPTION OCCURRED IN FILE HANDLING");
                        e.printStackTrace();
                    }
                }
                else {
                    requiredRecords.addAll(allRecords);
                }
                if (columns.length == 0) {
                    return requiredRecords;
                }
                else {
                    String[] recordRow;
                    StringBuilder newRecord = new StringBuilder();
                    for (String record : requiredRecords) {
                        recordRow = record.split("-");
                        for (int i = 0; i < columns.length; i++) {
                            Object obj = metaData.get(columns[i].trim());
                            if (i != columns.length-1) {
                                newRecord.append(recordRow[obj.getIndex()]).append("-");
                            }
                            else {
                                newRecord.append(recordRow[obj.getIndex()]);
                            }
                        }
                        requiredColumnRecords.add(newRecord.toString());
                        newRecord = new StringBuilder();
                    }
                    return requiredColumnRecords;
                }
            }
        }
        else {
            log.warn("Table "+tableName+" doesn't exist in "+databaseName);
            System.out.println("Table doesn't exist");
        }
        return requiredRecords;
    }

    public boolean checkTableName(String databaseName, String tableName) {
        return Files.exists(Paths.get(databaseName + "/" + tableName+".txt"));
    }

    public Map<String, Object> getTableMetaData(String databaseName, String tableName) {
        Map<String, Object> metaData = new HashMap<>();
        try {
            List<String> columnList = Files.readAllLines(Paths.get(databaseName + "/" + tableName+"meta.txt"));
            String[] columnData;
            Object obj;
            for (String line : columnList) {
                columnData = line.split("-");
                String columnName = columnData[1];
                String columnDataType = columnData[2];
                int columnIndex = Integer.parseInt(columnData[0]);
                obj = new Object(columnName,columnDataType,columnIndex);
                metaData.put(columnName,obj);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return metaData;

    }
}
