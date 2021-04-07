package SelectOperation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectParser {

    String query;
    private static Logger log = LogManager.getLogger(SelectParser.class);

    public SelectParser(String query) {
        this.query = query;
    }

    public List<String> parseQuery(String query) throws IOException {
        validateQuery(query);
        SelectQuery select = new SelectQuery();
        String tableName = fetchTableName(query);
        String[] requiredColumns = getColumnNames(query);
        String conditionColumnName = getWhereColumnName(query);
        String conditionColumnValue = getWhereColumnValue(query);
        return select.executeQuery(tableName, requiredColumns, conditionColumnName, conditionColumnValue);
    }

    public boolean validateQuery(String query) {

        //https://stackoverflow.com/questions/1506699/regular-expression-to-match-select-from-where-sql-queries
        String regex = "SELECT\\s+?[^\\s]+?\\s+?FROM\\s+?[^\\s]+?\\s+?WHERE.*";
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(query);
        boolean valid = match.find();
        if(valid) {
            return true;
        }
        else {
            return false;
        }
    }

    public String fetchTableName(String query) {

        int positionOfFrom = query.toLowerCase().indexOf("from");
        int positionOfSpace = query.indexOf(" ",positionOfFrom + 1);
        if (query.toLowerCase().contains("where")) {
            int positionOfWhere = query.toLowerCase().indexOf("where",positionOfSpace + 1);
            return query.substring(positionOfSpace + 1,positionOfWhere).toLowerCase().trim();
        }
        return query.substring(positionOfSpace + 1, query.length()).toLowerCase().trim();
    }

    private String[] getColumnNames(String query) {
        String[] columns = {};
        if (!query.contains("*")) {
            int positionOfAfterSelect = query.toLowerCase().indexOf(" ");
            int positionOfFrom = query.toLowerCase().indexOf("from", positionOfAfterSelect + 1);
            String columnsList = query.substring(positionOfAfterSelect + 1, positionOfFrom).toLowerCase().trim();
            columns = columnsList.split(",");
        }
        return columns;
    }

    public String getWhereColumnName(String query)
    {
        if (query.toLowerCase().contains("where"))
        {
            int positionOfWhere = query.toLowerCase().indexOf("where");
            int positionOfSpaceAfterWhere = query.indexOf(" ", positionOfWhere + 1);
            int positionOfEqual = query.indexOf("=", positionOfSpaceAfterWhere);
            return query.substring(positionOfSpaceAfterWhere + 1, positionOfEqual).toLowerCase().trim();
        }
        return null;
    }

    public String getWhereColumnValue(String query)
    {
        if (query.toLowerCase().contains("where"))
        {
            int positionOfEqual = query.indexOf("=");
            return query.substring(positionOfEqual + 1, query.length()).trim();
        }
        return null;
    }

}
