package SelectOperation;

public class Object {

    private String columnName;
    private String dataType;
    private int index;

    public Object(String columnName, String dataType, int index) {
        this.setColumnName(columnName);
        this.setDataType(dataType);
        this.setIndex(index);
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
