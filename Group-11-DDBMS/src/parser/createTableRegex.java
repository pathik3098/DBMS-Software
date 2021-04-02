package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class createTableRegex {
    public static void main(String args[]){
    //"^insert\sinto\s[a-z]*\sVALUES\s?\\(((([a-z0-9\\$]+)([,]*))+)[^,]\\);$";
    String createTable = "CREATE TABLE table_name (column1 datatype,column2 datatype,columnumn3 datatype);";


    String regex = "^CREATE\\sTABLE\\s[a-zA-Z]+";

    Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    Matcher m = re.matcher(createTable);

		while (m.find()) {
        System.out.println(m.group(0));
        }
    }
}
