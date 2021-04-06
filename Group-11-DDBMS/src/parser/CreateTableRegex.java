package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTableRegex {

/*
    ^create\stable\s(?!_)\\w*\\(((([a-z0-9]+)\s(int|String)\s?([,]*)\s?)+)(?<!,)\\);$
    String createTable = "CREATE TABLE tablename(column1 int,column2 String,column3 int);";
    String regex = "^create\\stable\\s\\\\w*\\\\(((([a-z0-9]+)\\s(int|String)\\s?([,]*)\\s?)+)(?<!,)\\\\);$";

    Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher m = re.matcher(createTable);
    boolean validity = m.find();
    System.out.println(validity);

    while (m.find())
    {
        System.out.println(m.group(0));
    }
*/
    public boolean checkTableRegex(String query)
    {
        String createTableQuery = query;
        Pattern pattern = Pattern.compile("^create\\stable\\s(?!_)\\\\w*\\\\(((([a-z0-9]+)\\s(int|String)\\s?([,]*)\\s?)+)(?<!,)\\\\)", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(createTableQuery);
        Boolean validity =match.find();

        if (validity)
        {
//            System.out.println("valid");
            return true;
        }
        else
        {
//            System.out.println("INVALID SYNTAX");
            return true;
        }
    }
}
