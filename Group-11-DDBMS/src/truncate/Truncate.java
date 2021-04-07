package truncate;

import fileHandlingOperation.FileHandlingOperations;
import parser.TruncateRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Truncate {

    TruncateRegex truncateRegex = new TruncateRegex();
    FileHandlingOperations fileOperation;
    public void executeTruncateQuery(String query, String databaseName) throws IOException {
        if(truncateRegex.queryChecker(query))
        {
            String[] str = query.split(" ");
            String tableName = str[2].replace(";", "");

            fileOperation = new FileHandlingOperations(databaseName, tableName.toLowerCase() + ".txt");
            if(fileOperation.isFilePresent())
            {
                Files.deleteIfExists(Paths.get(databaseName + "\\" + tableName));
                if (Files.notExists(Paths.get(databaseName + "\\" + tableName)))
                {
                    try
                    {
                        Files.createFile(Paths.get(databaseName + "\\" + tableName));
                        System.out.println(tableName + " table data deleted.");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    System.out.println("table does not exists in "
                        + databaseName + " database.");
                }
            }
            else
            {
                System.out.println("Table Not Present");
            }
        }
        else
        {
            System.out.println("Syntax Error");
        }

    }


}
