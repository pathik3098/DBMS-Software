package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseRegex {
    String useQuery;
    String regex;

    public boolean checkUseQuery(String query)
    {
        useQuery =query;
        String regex = "^USE\\s\\w";
        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = re.matcher(useQuery);
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
