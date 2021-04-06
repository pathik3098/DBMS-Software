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

public class DeleteQuery {

    private String Directory = "src/Database";
    Use db = new Use();
    String database = db.getCurrentDB();
    public boolean executeQuery(String tableName, String conditionColumnName, String conditionColumnValue) throws IOException {

        Path filePath = Paths.get(Directory + "/" +database+"/" +tableName+".txt");
        Charset charset = StandardCharsets.UTF_8;
        if (checkTableName(database,tableName)) {
            Map<String, Object> metaData = fetchMetaData(database,tableName);
            if (conditionColumnName != null) {
                try {
                    List<String[]> recordsAfterDelete = new ArrayList<>();
                    int ColumnPosition = metaData.get(conditionColumnName).getIndex();
                    List<String> records = Files.readAllLines(filePath,charset);
                    System.out.println(records);
                    for (int i = 0; i<records.size();i++)
                    {
                        String[] eachRow = records.get(i).split("-");
                        if (eachRow[ColumnPosition].equalsIgnoreCase(conditionColumnValue)) {

                            System.out.println(i);
                            System.out.println(records.get(i));
                            //records.remove(i);
                            //System.out.println(records.remove(i));
                        }
                        else {
                            recordsAfterDelete.add(eachRow);
                        }
                    }
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
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Files.deleteIfExists(filePath);
                Files.createFile(filePath);
                System.out.println("Invalid command");
                return false;
            }
        }
        else {
            System.out.println("Table doesn't exist");
            return false;
        }
        return true;
    }

    public boolean checkTableName(String database, String tableName) {
        return Files.exists(Paths.get(Directory + "/" +database+"/" +tableName+".txt"));
    }

    public Map<String, Object> fetchMetaData(String database, String tableName){
        //File file = new File(tableName + "meta.txt");
        Path filePath = Paths.get(Directory + "/" +database+"/" +tableName+"meta.txt");
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
