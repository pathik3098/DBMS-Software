package DeleteOperation;

import java.util.List;

public class DeleteParser {

    public void parseQuery(String query) {
        DeleteQuery delete = new DeleteQuery();
        String tableName = fetchTableName(query);
        String conditionColumnName = getWhereColumnName(query);
        String conditionColumnValue = getWhereColumnValue(query);
        delete.executeQuery(tableName,conditionColumnName,conditionColumnValue);
    }

    public String fetchTableName(String query) {

        int indexOfFrom = query.toLowerCase().indexOf("from");
        int indexOfSpace = query.indexOf(" ",indexOfFrom + 1);
        int indexOfWhere = query.toLowerCase().indexOf("where",indexOfSpace + 1);
        return query.substring(indexOfSpace + 1,indexOfWhere).toLowerCase().trim();
    }

    public String getWhereColumnName(String query)
    {
            int positionOfWhere = query.toLowerCase().indexOf("where");
            int positionOfSpaceAfterWhere = query.indexOf(" ", positionOfWhere + 1);
            int positionOfEqual = query.indexOf("=", positionOfSpaceAfterWhere);
            String columnName = query.substring(positionOfSpaceAfterWhere + 1, positionOfEqual).toLowerCase().trim();
            return columnName;
    }

    public String getWhereColumnValue(String query)
    {
            int indexOfEqual = query.indexOf("=");
            String columnValue = query.substring(indexOfEqual + 1, query.length()).trim();
            return columnValue;
    }



}
