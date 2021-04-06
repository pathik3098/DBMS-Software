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

    final private String DATABASE_DIRECTORY = "E:\\DAL WINTER SEM 1\\Data Management\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\";
    private String dbName = "";
    private String fileName = "";

    public FileHandlingOperations(String dbName, String fileName)
    {
        this.dbName = dbName;
        this.fileName = fileName;
    }

    public boolean isFilePresent()
    {
        return Files.exists(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
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
            }
            Files.writeString(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName), tableData,
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
            List<String> lines = Files.readAllLines(Paths.get(DATABASE_DIRECTORY + dbName + "\\" + fileName));
            ColumnData columnData;

            String columnName;
            String columnType;
            int columnIndex;

            for (String colLine : lines)
            {
            	//System.out.println(colLine);
                String[] columnDataArray = colLine.split("\\-");
                columnIndex = Integer.parseInt(columnDataArray[0]);
                columnName = columnDataArray[1];
                columnType = columnDataArray[2];
                columnData = new ColumnData(columnName, columnType, columnIndex);
                //System.out.println(columnName + columnIndex + columnType);
                column.put(columnIndex, columnData);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return column;

    }
}
