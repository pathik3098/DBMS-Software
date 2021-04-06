package CreateOperation;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Use {
    String useQuery;
    public static String currentDB;

    public Use(String query)
    {
        this.useQuery = query;
    }

    public void execute()
    {
        String path = "src/Database";
        String[] statement = useQuery.split(" ");
        path = path + "/" + statement[1];
        boolean isDatabasePresent = Files.isDirectory(Paths.get(path));
        if(isDatabasePresent)
        {
            currentDB = path;
        }
        else
        {
            System.out.println(statement[1]+" database not present");
        }
    }

}
