package CreateOperation;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Use {
    String useQuery;
    public static String currentDB;
    public static String currentRemoteDB;

    public Use(String query)
    {
        this.useQuery = query;
    }
    public Use() {}
    public void setCurrentDB(String database) {
        this.currentDB = database;
    }

    public String getCurrentDB() {
        return currentDB;
    }
    public void execute()
    {
        String path = "src/LocalSite";
        String remotePath = "src/RemoteSite";
        String[] statement = useQuery.split(" ");
        path = path + "/" + statement[1];
        remotePath = remotePath + "/" + statement[1];
        boolean isDatabasePresent = Files.isDirectory(Paths.get(path));
        if(isDatabasePresent)
        {
            currentDB = path;
            currentRemoteDB = remotePath;
            setCurrentDB(currentDB);
        }
        else
        {
            System.out.println(statement[1]+"database not present");
        }
    }
}
