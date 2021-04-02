package CreateOperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateTable {
    String [] createsql = new String[100];
    String path = "D:\\Database";
    String tablemetapath,tablepath;
    boolean result;

    public CreateTable()
    {
        createsql[0] = "TestDatabase";
        createsql[1] = "create";
        createsql[2] = "table";
        createsql[3] = "tablename";
        createsql[4] = "column1";
        createsql[5] = "int";
        createsql[6] = "column2";
        createsql[7] = "String";
        tablemetapath = path+"\\"+createsql[0]+"\\"+createsql[3]+"meta"+".txt";
        tablepath = path+"\\"+createsql[0]+"\\"+createsql[3]+".txt";
    }

    public void createtable()
    {
        File file = new File(tablemetapath);
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


        try
        {
            if(result)
            {
                FileWriter myWriter = new FileWriter(tablemetapath);
                for(int i = 4; i< createsql.length; i=i+2)
                {
                    myWriter.write(createsql[i]+"-"+createsql[i+1]+"\n");
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        File file1 = new File(tablepath);
        try
        {
            result = file1.createNewFile();
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
