package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ERDQueryParser {

    private static final String ERD_REGEX = "^ERD [A-Z_]*$";

    public boolean queryChecker(String query)
    {
        Pattern pattern = Pattern.compile(ERD_REGEX.toUpperCase());
        Matcher matcher = pattern.matcher(query.toUpperCase());
        if (matcher.find())
        {
            return !matcher.group(0).isBlank();
        }
        return false;
    }

}
