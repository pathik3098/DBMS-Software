package CreateOperation;

import parser.CreateDatabaseRegex;
import parser.CreateTableRegex;
import parser.UseRegex;

import java.util.Scanner;

public class CreateMain {

    public static void main(String args[]){
        while(true) {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter Query: ");
            String query = s.nextLine();
            String[] split = query.split(" ");

            switch (split[0]) {
                case "CREATE":

                 if(split[1].equals("DATABASE"))
                 {
                    CreateDatabaseRegex DBregex = new CreateDatabaseRegex();
                    boolean validQuery = DBregex.checkDBQuery(query);
                    if(validQuery)
                    {
                        CreateDatabase createDB= new CreateDatabase(query);
                        createDB.execute();
                    }
                    else
                    {
                        System.out.println("Invalid Query, PLease try again ");
                    }
                 }

                 else if(split[1].equals("TABLE"))
                 {
                     CreateTableRegex tableRegex = new CreateTableRegex();
                     boolean validQuery = tableRegex.checkTableRegex(query);
                     if (validQuery) {
                         CreateTable create = new CreateTable(query);
                         create.execute();
                     } else {
                         System.out.println("Invalid Query, PLease try again ");
                     }
                 }



                    break;
                case "USE":
                    UseRegex useRe = new UseRegex();
                    boolean valid = useRe.checkUseQuery(query);
                    if (valid) {
                        Use useDB = new Use(query);
                        useDB.execute();
                    } else {
                        System.out.println("Invalid Query, PLease try again ");
                    }
            }
        }
    }
}
