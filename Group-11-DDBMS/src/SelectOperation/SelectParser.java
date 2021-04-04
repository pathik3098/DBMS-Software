package SelectOperation;

import java.util.List;

public class SelectParser {

    String query;
    String database;
    SelectQuery select = new SelectQuery();
    private String Directory;

    public SelectParser(String query) {
        this.query = query;
    }

    public List<String> parseQuery(String query) {
        SelectQuery select = new SelectQuery();
        String tableName = fetchTableName(query);
        String[] requiredColumns = getColumnNames(query);
        String conditionColumnName = getWhereColumnName(query);
        String conditionColumnValue = getWhereColumnValue(query);
        List<String> executedResults = select.executeQuery(tableName,requiredColumns,conditionColumnName,conditionColumnValue);
        return executedResults;
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
        String[] columns = null;
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
