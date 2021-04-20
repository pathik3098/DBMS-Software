package CreateOperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import static CreateOperation.Use.currentDB;
import static CreateOperation.Use.currentRemoteDB;

public class CreateTable {
    String query;
    ArrayList<String> queryTokens = new ArrayList<String>();
    String path = currentDB;
    String remotePath = currentRemoteDB;
    String tablemetapath, tablepath;
    String tableRemotePath, tableRemoteMetaPath;
    String[] getDataInsideBracket;
    boolean result;

    public CreateTable(String query) {
        this.query = query;
        tokenize();
        tablemetapath = path + "\\" + queryTokens.get(2).toLowerCase() + "meta" + ".txt";
        tablepath = path + "\\" + queryTokens.get(2).toLowerCase() + ".txt";
        tableRemoteMetaPath = remotePath + "\\" + queryTokens.get(2).toLowerCase() + "meta" + ".txt";
        tableRemotePath = remotePath + "\\" + queryTokens.get(2).toLowerCase() + ".txt"  ;
    }

    private void tokenize() {
        String[] getPartBeforeRoundBracket = query.split("\\((.*?)\\);");
        String[] getTableName = getPartBeforeRoundBracket[0].split(" ");
        queryTokens.addAll(Arrays.asList(getTableName));

        String[] getPartInsideRoundBracket = query.split("\\(");
        String intermediateResult = getPartInsideRoundBracket[1];
        String[] removeLastCurlyBracket = intermediateResult.split("\\)");
        String[] getColumnTokens = removeLastCurlyBracket[0].split(",");
        getDataInsideBracket = removeLastCurlyBracket[0].split(",");

        for (String j : getColumnTokens) {
            String[] abc = j.split(" ");
            queryTokens.addAll(Arrays.asList(abc));
        }
    }

    public String foreignKeyTableName() {
        String foreignKeyTableName = null;

        for(int i=0; i < getDataInsideBracket.length;i++)
        {
            String row = getDataInsideBracket[i];
            if (row.toUpperCase().contains("FK")) {
                String[] foreignKey = row.split(" ");
                foreignKeyTableName = foreignKey[3].toLowerCase();
            }
        }
        return foreignKeyTableName;
    }

    public boolean checkIfForeignTableExists(String fTableName) {
        return Files.exists(Paths.get(currentDB + "/" + fTableName + ".txt"));
    }

    public void execute() {

        File directory=new File(currentDB);
        int fileCount = 0;
        if(directory.list() != null)
        {
            fileCount=directory.list().length;
        }

        if(fileCount<9)
        {
            tableCreationCheck();
        }

        if(fileCount>=9)
        {
            createTableOperation(currentRemoteDB,tableRemoteMetaPath,tableRemotePath);
        }
    }

    public void tableCreationCheck()
    {
        String foreignTableName = foreignKeyTableName();
        if(foreignTableName == null)
        {
            createTableOperation(currentDB,tablemetapath,tablepath);
        }

        else
        {
            boolean checkForeignTable = checkIfForeignTableExists(foreignTableName);
            if(checkForeignTable)
            {
                createTableOperation(currentDB,tablemetapath,tablepath);
            }
            else
            {
                System.out.println("Foreign Table doesn't exist");
            }
        }
    }

    public void createTableOperation(String currentDBPath, String currentMetaPath, String currentFilePath)
    {
        File file = new File(currentMetaPath);
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (result) {
                FileWriter myWriter = new FileWriter(currentMetaPath);
                for (int i = 0; i < getDataInsideBracket.length; i++) {
                    Integer a = i;
                    String index = a.toString();
                    myWriter.write(index);
                    String[] abc = getDataInsideBracket[i].split(" ");
                    for (String t : abc) {
                        myWriter.write("-" + t);
                    }
                    myWriter.write("\n");
                }
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File createFile = new File(currentFilePath);
        try {
            result = createFile.createNewFile();
            if (result) {
                System.out.println("table succesfully created");
            } else {
                System.out.println("table already exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String dumpFilePath = currentDB + "/" + "SQLdump.txt";
        String dumpRemoteFilePath = currentRemoteDB + "/" + "SQLdump.txt";
        Path path = Paths.get(dumpFilePath);
        Path remotePath = Paths.get(dumpRemoteFilePath);

        try {
            Files.writeString(path, query + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            Files.writeString(remotePath, query + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("Error inserting create query in sql dump file");
        }
    }
}
