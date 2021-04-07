package erd;

import fileHandlingOperation.FileHandlingOperations;
import insertOperation.ColumnData;
import parser.ERDQueryParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ERDOperation {

    ERDQueryParser erdQueryParser = new ERDQueryParser();

    public void erdQueryParser(String query)
    {
        if (erdQueryParser.queryChecker(query.toUpperCase()))
        {
            String databaseName = getDbName(query);
            if(isDirectoryPresent(databaseName))
            {
                generateErdFile(databaseName);
            }
            else
            {
                System.out.println(databaseName + "\"Database does not exist\"");
            }

        } else {
            System.out.println("Syntax Error");
        }
    }

    public String getDbName(String query) {
        String[] substring = query.toUpperCase().split(" ");
        return substring[2].trim();
    }

    public boolean isDirectoryPresent(String databaseName) {
        return Files.exists(Paths.get(databaseName));
    }

    public void generateErdFile(String databaseName)
    {
        List<File> fileList;
        try
        {
            //Reference - https://stackabuse.com/java-list-files-in-a-directory/
            //Reference - https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
            //Reference - https://stackoverflow.com/questions/203030/best-way-to-list-files-in-java-sorted-by-date-modified

            fileList = Files.list(Paths.get(databaseName))
                    .filter(Files -> Files.toString().endsWith("meta"))
                    .sorted(Comparator.comparingLong(filePath -> filePath.toFile().lastModified()))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            StringBuilder fileContent = new StringBuilder();
            for (File metadataFile : fileList)
            {
                FileHandlingOperations fileOperation = new FileHandlingOperations(databaseName, metadataFile.getName());
                Map<Integer, ColumnData> tableMetaData = fileOperation.readMetaData();
                StringBuilder columnReferenceBuilder = new StringBuilder();
                for (ColumnData column : tableMetaData.values())
                {
                    if (column.getConstraint().equalsIgnoreCase("FK")) {
                        String tableName = metadataFile.getName().split("-")[0];
                        columnReferenceBuilder.append(tableName).append(" ---- references ----> ").append(column.getForeignKeyTableName().toLowerCase());
                    }
                }
                if (columnReferenceBuilder.toString().length() > 0)
                {
                    fileContent.append(columnReferenceBuilder.toString()).append("\n");
                }
            }
            this.saveToFile(fileContent.toString(), databaseName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void saveToFile(String fileContent, String databaseName) {
        try {
            Files.writeString(Paths.get(databaseName + "_erd.txt"), fileContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
