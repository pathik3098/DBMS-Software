package CreateOperation;

import java.io.File;
import java.io.IOException;

public class CreateDatabase {
    String databaseName;
    String localPath = "src/Database";
    String remotePath = "src/RemoteSite";
    String query;
    String[] statement;

    public CreateDatabase(String query){
        this.query = query;
    }

    public void execute(){
        statement = query.split(" ");
        databaseName = statement[2];
        localPath = localPath+"/"+ databaseName;
        remotePath = remotePath+"/"+databaseName;
        File remoteFile = new File(remotePath);
        File file = new File(localPath);
        boolean bool = file.mkdir();
        boolean isDatabaseCreated = remoteFile.mkdir();

        if(bool && isDatabaseCreated)
        {
            System.out.println("Database successfully created");
            String dumpPath = localPath + "/" + "SQLdump" + ".txt";
            File dumpFile = new File(dumpPath);
            try
            {
                boolean result = dumpFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else{
            System.out.println("Error in creating database");
        }
    }
}
