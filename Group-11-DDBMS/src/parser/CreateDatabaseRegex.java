package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDatabaseRegex {
    String createDBQuery;
    String regex;

    public boolean checkDBQuery(String query)
    {
        createDBQuery =query;
        String regex = "^CREATE\\sDatabase\\s[a-zA-Z]+";
        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = re.matcher(createDBQuery);
        Boolean valid = m.find();
        if (valid)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
