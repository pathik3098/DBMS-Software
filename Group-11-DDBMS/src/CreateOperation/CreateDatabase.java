package CreateOperation;

import java.io.File;
import java.io.IOException;

public class CreateDatabase {
    String databaseName;
    String localPath = "src/LocalSite";
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
        boolean islocalDatabaseCreated = file.mkdir();
        boolean isRemoteDatabaseCreated = remoteFile.mkdir();

        if(islocalDatabaseCreated && isRemoteDatabaseCreated)
        {
            System.out.println("Database successfully created");
            String dumpLocalPath = localPath + "/" + "SQLdump" + ".txt";
            String dumpRemotePath = remotePath + "/" + "SQLdump" + ".txt";
            File dumpLocalFile = new File(dumpLocalPath);
            File dumpRemoteFile = new File(dumpRemotePath);
            try
            {
                boolean result1 = dumpLocalFile.createNewFile();
                boolean result2 = dumpRemoteFile.createNewFile();
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
