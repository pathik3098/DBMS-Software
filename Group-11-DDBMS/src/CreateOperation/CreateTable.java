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

public class CreateTable {
    String query;
    ArrayList<String> queryTokens = new ArrayList<String>();
    String path = currentDB;
    String tablemetapath, tablepath;
    String[] getDataInsideBracket;
    boolean result;

    public CreateTable(String query) {
        this.query = query;
        tokenize();
        tablemetapath = path + "\\" + queryTokens.get(2).toLowerCase() + "meta" + ".txt";
        tablepath = path + "\\" + queryTokens.get(2).toLowerCase() + ".txt";
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
        int length = getDataInsideBracket.length;
        length = length-1;
        String lastRow = getDataInsideBracket[length];
        String foreignKeyTableName = null;

        if (lastRow.toUpperCase().contains("FK")) {
            String[] foreignKey = lastRow.split(" ");
            foreignKeyTableName = foreignKey[3].toLowerCase();
        }
        System.out.println(foreignKeyTableName);
        return foreignKeyTableName;
    }

    public boolean checkIfForeignTableExists(String fTableName) {
        return Files.exists(Paths.get(currentDB + "/" + fTableName + ".txt"));
    }

    public void execute() {

        String foreignTableName = foreignKeyTableName();

        if(foreignTableName == null)
        {
            createTableOperation();
        }

        else
        {
            boolean checkForeignTable = checkIfForeignTableExists(foreignTableName);
            if(checkForeignTable)
            {
                createTableOperation();
            }
            else
            {
                System.out.println("Foreign Table doesn't");
            }
        }
    }

    public void createTableOperation()
    {
        File file = new File(tablemetapath);
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (result) {
                FileWriter myWriter = new FileWriter(tablemetapath);
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

        File createFile = new File(tablepath);
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
        Path path = Paths.get(dumpFilePath);

        try {
            Files.writeString(path, query + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("Error inserting create query in sql dump file");
        }
    }
}
