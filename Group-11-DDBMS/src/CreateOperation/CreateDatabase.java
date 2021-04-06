package CreateOperation;

import java.io.File;

public class CreateDatabase {
    String databaseName;
    String path = "src/Database";
    String query;
    String[] statement;

    public CreateDatabase(String query){
        this.query = query;
    }

    public void execute(){
        statement = query.split(" ");
        databaseName = statement[2];
        path = path+"/"+ databaseName;
        File file = new File(path);
        boolean bool = file.mkdir();

        if(bool)
        {
            System.out.println("Database successfully created");
        }

        else{
            System.out.println("Error in creating database");
        }
    }
}
