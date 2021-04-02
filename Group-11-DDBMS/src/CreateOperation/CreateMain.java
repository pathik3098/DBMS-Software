package CreateOperation;

public class CreateMain {
    public static void main(String args[]){
        /*
        String [] createDatabaseStatement = new String[3];
        createDatabaseStatement[0]="CREATE";
        createDatabaseStatement[1]="DATABASE";
        createDatabaseStatement[2]="TestDatabase";

        CreateDatabase d = new CreateDatabase(createDatabaseStatement);

        d.createDatabase();
        */

        CreateTable table = new CreateTable();
        table.createtable();
    }
}
