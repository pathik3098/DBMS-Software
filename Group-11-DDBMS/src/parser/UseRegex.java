package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseRegex {
    String useQuery;
    String regex;

//    public static void main(String args[])
//    {
//        String useQuery ="USE abcd;";
//        String regex = "^USE\\s\\w";
//        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Matcher m = re.matcher(useQuery);
//        Boolean valid = m.find();
//        if (valid)
//        {
//            System.out.print("valid");
//        }
//        else
//        {
//            System.out.print("invalid");
//        }
//    }

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
