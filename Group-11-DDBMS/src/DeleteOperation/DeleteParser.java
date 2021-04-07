package DeleteOperation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteParser {

    private static Logger log = LogManager.getLogger(DeleteParser.class);

    public boolean parseQuery(String query) throws IOException {
        log.info("Parsing the Delete query");
        DeleteQuery delete = new DeleteQuery();
        String tableName = fetchTableName(query);
        String conditionColumnName = getColumnName(query);
        String conditionColumnValue = getColumnValue(query);
        delete.executeQuery(tableName,conditionColumnName,conditionColumnValue);
        return true;
    }

    public String fetchTableName(String query) {

        int positionOfFrom = query.toLowerCase().indexOf("from");
        int positionOfSpace = query.indexOf(" ",positionOfFrom + 1);
        if (query.toLowerCase().contains("where")) {
            int indexOfWhere = query.toLowerCase().indexOf("where",positionOfSpace + 1);
            return query.substring(positionOfSpace + 1,indexOfWhere).toLowerCase().trim();
        }
        return query.substring(positionOfSpace + 1, query.length()).toLowerCase().trim();
    }

    public String getColumnName(String query)
    {
        if (query.contains("where")) {
            int positionOfWhere = query.toLowerCase().indexOf("where");
            int positionOfSpaceAfterWhere = query.indexOf(" ", positionOfWhere + 1);
            int positionOfEqual = query.indexOf("=", positionOfSpaceAfterWhere);
            return query.substring(positionOfSpaceAfterWhere + 1, positionOfEqual).toLowerCase().trim();
        }
        return null;
    }

    public String getColumnValue(String query)
    {
        if (query.contains("where")) {
            int positionOfEqual = query.indexOf("=");
            return query.substring(positionOfEqual + 1, query.length()).trim();
        }
        return null;
    }



}
