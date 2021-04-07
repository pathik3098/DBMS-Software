package SelectOperation;

import CreateOperation.CreateDatabase;
import CreateOperation.Use;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectQuery {

    String query;
    //private String Directory = "src/Database";
    Use database = new Use();
    String databaseName = database.getCurrentDB();

    public List<String> executeQuery(String tableName, String[] requiredColumns, String conditionColumnName, String conditionColumnValue) throws IOException {
        List<String> allRecords = new ArrayList<>();
        List<String> requiredRecords = new ArrayList<>();
        List<String> requiredColumnRecords = new ArrayList<>();
        if (checkTableName(databaseName,tableName)) {
            String[] eachRow;
            Map<String, Object> metaData = getTableMetaData(databaseName,tableName);
            String[] columns = requiredColumns;
            //boolean isColumnsAvailable = columnsAvailable(columns, metaData);
            if (columns.length == 0) {
                allRecords = Files.readAllLines(Paths.get(databaseName + "/" + tableName + ".txt"));
                for (String record : allRecords) {
                    System.out.println(record);
                }
            }
            else {
                if (conditionColumnName != null) {
                    try {
                        //File file = new File(databaseName,tableName + ".txt");
                        allRecords = Files.readAllLines(Paths.get( databaseName + "/" + tableName+".txt"));
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
//            else {
//                allRecords = Files.readAllLines(Paths.get(Directory + "/" + databaseName + "/" + tableName+".txt"));
//                for (String record : allRecords) {
//                    System.out.println(record);
//                }
//                //System.out.println("Invalid selection");
//            }
        }
        else {
            System.out.println("Table doesn't exist");
        }
        return requiredRecords;
    }

    public boolean checkTableName(String databaseName, String tableName) {
        return Files.exists(Paths.get(databaseName + "/" + tableName+".txt"));
    }

    public Map<String, Object> getTableMetaData(String databaseName, String tableName) {
        //File file = new File(tableName.toLowerCase() + "meta.txt");
        Map<String, Object> metaData = new HashMap<>();
        try {
            //correct the code here
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
