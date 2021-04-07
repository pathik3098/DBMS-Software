package DeleteOperation;

import java.io.IOException;

public class DeleteParser {

    public void parseQuery(String query) throws IOException {
        DeleteQuery delete = new DeleteQuery();
        String tableName = fetchTableName(query);
        String conditionColumnName = getColumnName(query);
        String conditionColumnValue = getColumnValue(query);
        delete.executeQuery(tableName,conditionColumnName,conditionColumnValue);
    }

    public String fetchTableName(String query) {

//        int positionOfFrom = query.toLowerCase().indexOf("from");
//        int positionOfSpace = query.indexOf(" ",positionOfFrom + 1);
//        int positionOfWhere = query.toLowerCase().indexOf("where",positionOfSpace + 1);
//        return query.substring(positionOfSpace + 1,positionOfWhere).toLowerCase().trim();

        int indexOfFrom = query.toLowerCase().indexOf("from");
        int indexOfSpace = query.indexOf(" ",indexOfFrom + 1);
        if (query.toLowerCase().contains("where")) {
            int indexOfWhere = query.toLowerCase().indexOf("where",indexOfSpace + 1);
            return query.substring(indexOfSpace + 1,indexOfWhere).toLowerCase().trim();
        }
        return query.substring(indexOfSpace + 1, query.length()).toLowerCase().trim();
    }

    public String getColumnName(String query)
    {
//            int positionOfWhere = query.toLowerCase().indexOf("where");
//            int positionOfSpaceAfterWhere = query.indexOf(" ", positionOfWhere + 1);
//            int positionOfEqual = query.indexOf("=", positionOfSpaceAfterWhere);
//            String columnName = query.substring(positionOfSpaceAfterWhere + 1, positionOfEqual).toLowerCase().trim();
//            return columnName;

        if (query.contains("where")) {
            int positionOfWhere = query.toLowerCase().indexOf("where");
            int positionOfSpaceAfterWhere = query.indexOf(" ", positionOfWhere + 1);
            int positionOfEqual = query.indexOf("=", positionOfSpaceAfterWhere);
            String columnName = query.substring(positionOfSpaceAfterWhere + 1, positionOfEqual).toLowerCase().trim();
            return columnName;
        }
        return null;
    }

    public String getColumnValue(String query)
    {
        if (query.contains("where")) {
            int positionOfEqual = query.indexOf("=");
            String columnValue = query.substring(positionOfEqual + 1, query.length()).trim();
            return columnValue;
        }
        return null;
    }



}
