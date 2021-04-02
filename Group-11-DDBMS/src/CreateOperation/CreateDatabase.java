package CreateOperation;

import java.io.File;

public class CreateDatabase {
    String databaseName;
    String path = "D:\\Database";

    String[] statement;

    public CreateDatabase(String[] s){
        statement = s;
        databaseName = statement[2];
        path = path+"\\"+ databaseName;
        System.out.println(path);
    }

    public void createDatabase(){
        File file = new File(path);
        boolean bool = file.mkdir();

        if(bool)
        {
            System.out.println("Database succesfully created");
        }

        else{
            System.out.println("Error in creating database");
        }
    }


}
