package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateTableRegex {

    public boolean checkTableRegex(String query)
    {
        String createTableQuery = query;
        Pattern pattern = Pattern.compile("^create\\stable\\s(?!_)\\\\w*\\\\(((([a-z0-9]+)\\s(int|String)\\s?([,]*)\\s?)+)(?<!,)\\\\)", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(createTableQuery);
        Boolean validity =match.find();

        if (validity)
        {
            return true;
        }
        else
        {
            return true;
        }
    }
}
