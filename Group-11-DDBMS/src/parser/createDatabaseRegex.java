package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createDatabaseRegex {
    public static void main(String args[]){
        //"^insert\sinto\s[a-z]*\sVALUES\s?\\(((([a-z0-9\\$]+)([,]*))+)[^,]\\);$";
        String createDatabase = "CREATE Database databasename";


        String regex = "^CREATE\\sDatabase\\s[a-zA-Z]+";

        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        Matcher m = re.matcher(createDatabase);

        while (m.find()) {
            System.out.println(m.group(0));
        }
    }
}
