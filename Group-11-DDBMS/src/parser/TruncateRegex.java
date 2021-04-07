package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TruncateRegex {
    private static final String TRUNCATE_QUERY_REGEX = "^TRUNCATE TABLE [A-Z_]*$";

    public boolean queryChecker(String query)
    {
        Pattern pattern = Pattern.compile(TRUNCATE_QUERY_REGEX);
        Matcher matcher = pattern.matcher(query.toUpperCase());
        if (matcher.find())
        {
            return !matcher.group(0).isBlank();
        }
        return false;
    }
}
