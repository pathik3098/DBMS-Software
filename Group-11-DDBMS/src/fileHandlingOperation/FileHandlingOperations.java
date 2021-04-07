package fileHandlingOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import insertOperation.ColumnData;

public class FileHandlingOperations {

    //final private String DATABASE_DIRECTORY = "E:\\DAL WINTER SEM 1\\Data Management\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\";
    private String dbName = "";
    private String fileName = "";

    public FileHandlingOperations(String dbName, String fileName)
    {
        this.dbName = dbName;
        this.fileName = fileName;
    }

    public boolean isFilePresent()
    {
        return Files.exists(Paths.get(dbName + "/" + fileName));
    }


    public void writeTableData(String[] dataArray)
    {
        try
        {
            String tableData = "";
            String separator = "-";
            for (int i = 0; i < dataArray.length; i++)
            {
                if (i == dataArray.length - 1)
                {
                    tableData += dataArray[i].trim();
                    tableData += "\n";
                }
                else
                {
                    tableData += dataArray[i].trim() + separator;
                }
                System.out.println(tableData);
            }
            Files.writeString(Paths.get(dbName + "/" + fileName), tableData,
                    StandardOpenOption.APPEND);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
    
    public Map<Integer, ColumnData> readMetaData() {
        Map<Integer, ColumnData> column = new HashMap<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(dbName + "/" + fileName));
            ColumnData columnData;

            String columnName;
            String columnType;
            int columnIndex;
            String columnConstraint;
            String columnForeignTableName;

            for (String colLine : lines)
            {
            	//System.out.println(colLine);
                String[] columnDataArray = colLine.split("-");
                columnIndex = Integer.parseInt(columnDataArray[0]);
                columnName = columnDataArray[1];
                columnType = columnDataArray[2];
                columnConstraint = columnDataArray[3];
                columnForeignTableName = columnDataArray[4];
                columnData = new ColumnData(columnName, columnType, columnIndex, columnConstraint, columnForeignTableName);
                //System.out.println(columnName + columnIndex + columnType);
                column.put(columnIndex, columnData);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return column;
    }

    public void writeTableMetaData(List<ColumnData> columns) {
        try {
            String tableMetaData = "";
            String separator = "-";
            for (ColumnData column : columns) {
                tableMetaData += column.getIndex() + separator;
                tableMetaData += column.getName() + separator;
                tableMetaData += column.getType() + separator;
                tableMetaData += column.getConstraint() + separator;
                tableMetaData += column.getForeignKeyTableName() + separator;
                tableMetaData += "\n";
            }
            Files.writeString(Paths.get(dbName + "\\" + fileName), tableMetaData);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
