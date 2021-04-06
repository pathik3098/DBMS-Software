package SelectOperation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQuery {

    String query;
    String database;//handle this

    public List<String> executeQuery(String tableName, String[] requiredColumns, String conditionColumnName, String conditionColumnValue) {
        List<String> allRecords = new ArrayList<>();
        List<String> requiredRecords = new ArrayList<>();
        List<String> requiredColumnRecords = new ArrayList<>();
        String Directory = "dbms\\";
        if (checkTableName(database,tableName)) {
            String[] eachRow;
            Map<String, Object> metaData = getTableMetaData(database,tableName);
            String[] columns = requiredColumns;
            boolean isColumnsAvailable = columnsAvailable(columns, metaData);
            if (isColumnsAvailable) {
                if (conditionColumnName != null) {
                    try {
                        File file = new File(database,tableName + ".txt");
                        allRecords = Files.readAllLines(Paths.get(Directory + database + "\\" + file));
                        for (String record : allRecords) {
                            eachRow = record.split("-");
                            for (int i = 0; i < eachRow.length;i++) {
                                if (eachRow[i].equals(conditionColumnValue)) {
                                    requiredRecords.add(record);
                                }
                            }
                        }
                    }
                    catch (IOException e) {
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
                    String newRecord = "";
                    for (String record : requiredRecords) {
                        recordRow = record.split("-");
                        for (int i = 0; i < columns.length; i++) {
                            Object obj = metaData.get(columns[i].trim());
                            if (i != columns.length-1) {
                                newRecord += recordRow[obj.getIndex()] + "-";
                            }
                            else {
                                newRecord += recordRow[obj.getIndex()];
                            }
                        }
                        requiredColumnRecords.add(newRecord);
                        newRecord = "";
                    }
                    return requiredColumnRecords;
                }
            }
            else {
                System.out.println("Invalid selection");
            }
        }
        else {
            System.out.println("Table doesn't exist");
        }
        return requiredRecords;
    }

    public boolean checkTableName(String database, String tableName) {
        File file = new File(database,tableName + ".txt");
        return file.exists();
    }

    public Map<String, Object> getTableMetaData(String database, String tableName) {
        File file = new File(tableName.toLowerCase() + "meta.txt");
        Map<String, Object> metaData = new HashMap<>();
        try {
            List<String> columnList = Files.readAllLines(Paths.get("\\"+tableName+"meta.txt"));
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

    public boolean columnsAvailable(String[] columns, Map<String,Object> tableMetaData) {
        boolean present = true;
        for (int i = 0; i < columns.length; i++) {
            if (!tableMetaData.containsKey(columns[i].trim())) {
                present = false;
            }
        }
        return present;
    }
}
