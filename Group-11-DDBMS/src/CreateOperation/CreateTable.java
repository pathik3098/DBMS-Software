package CreateOperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static CreateOperation.Use.currentDB;

public class CreateTable {
    String query;
    ArrayList<String> queryTokens = new ArrayList<String>();
    String path = currentDB;
    String tablemetapath,tablepath;
    boolean result;

    public CreateTable(String query)
    {
        this.query = query;
        tokenize();
        tablemetapath = path+"\\"+queryTokens.get(2)+"meta"+".txt";
        tablepath = path+"\\"+ queryTokens.get(2)+".txt";
    }

    private void tokenize()
    {
        String[] getPartBeforeRoundBracket=query.split("\\((.*?)\\);");
        String[]  getTableName= getPartBeforeRoundBracket[0].split(" ");
        queryTokens.addAll(Arrays.asList(getTableName));

        String[] getPartInsideRoundBracket = query.split("\\(");
        String intermediateResult = getPartInsideRoundBracket[1];
        String[] removeLastCurlyBracket = intermediateResult.split("\\)");
        String[] getColumnTokens = removeLastCurlyBracket[0].split(",");

        for(String j : getColumnTokens)
        {
            String[] abc = j.split(" ");
            queryTokens.addAll(Arrays.asList(abc));
        }
    }

    public void execute()
    {
        createFile(tablemetapath);

        try
        {
            if(result)
            {
                FileWriter myWriter = new FileWriter(tablemetapath);
                for(int i = 3; i< queryTokens.size(); i=i+2)
                {
                    myWriter.write(queryTokens.get(i)+"-"+queryTokens.get(i+1)+"\n");
                }
                myWriter.close();
                //System.out.println("Successfully wrote to the file.");
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        createFile(tablepath);
    }

    private void createFile(String filePath) {
        File file = new File(filePath);
        try
        {
            result = file.createNewFile();
            if(result)
            {
                System.out.println("table succesfully created");
            }
            else
            {
                System.out.println("table already exist");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
