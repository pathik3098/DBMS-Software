package SelectOperation;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectParser {

    String query;
    //private static Logger log = LogManager.getLogger(SelectParser.class);

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
        List<String> executedResults = select.executeQuery(tableName,requiredColumns,conditionColumnName,conditionColumnValue);
        return executedResults;
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

        int indexOfFrom = query.toLowerCase().indexOf("from");
        int indexOfSpace = query.indexOf(" ",indexOfFrom + 1);
        if (query.toLowerCase().contains("where")) {
            int indexOfWhere = query.toLowerCase().indexOf("where",indexOfSpace + 1);
            return query.substring(indexOfSpace + 1,indexOfWhere).toLowerCase().trim();
        }
        return query.substring(indexOfSpace + 1, query.length()).toLowerCase().trim();
    }

    private String[] getColumnNames(String query) {
        String[] columns = {};
        if (query.contains("*")) {
            return columns;
        }
        else {
            int indexOfAfterSelect = query.toLowerCase().indexOf(" ");
            int indexOfFrom = query.toLowerCase().indexOf("from",indexOfAfterSelect + 1);
            String columnsList = query.substring(indexOfAfterSelect + 1,indexOfFrom).toLowerCase().trim();
            columns = columnsList.split(",");
            return columns;
        }
    }

    public String getWhereColumnName(String query)
    {
        if (query.toLowerCase().contains("where"))
        {
            int indexOfWhere = query.toLowerCase().indexOf("where");
            int indexOfSpaceAfterWhere = query.indexOf(" ", indexOfWhere + 1);
            int indexOfEqual = query.indexOf("=", indexOfSpaceAfterWhere);
            String columnName = query.substring(indexOfSpaceAfterWhere + 1, indexOfEqual).toLowerCase().trim();
            return columnName;
        }
        return null;
    }

    public String getWhereColumnValue(String query)
    {
        if (query.toLowerCase().contains("where"))
        {
            int indexOfEqual = query.indexOf("=");
            String columnValue = query.substring(indexOfEqual + 1, query.length()).trim();
            return columnValue;
        }
        return null;
    }

}
