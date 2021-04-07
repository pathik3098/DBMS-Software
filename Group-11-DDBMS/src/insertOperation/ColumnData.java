package insertOperation;

public class ColumnData {
	
	    private final String name;
	    private final String type;
	    private final int index;
	    private final String constraint;
		private final String foreignKeyTableName;

	public ColumnData(String name, String type, int index, String constraint, String foreignKeyTableName) {
	        this.name = name;
	        this.type = type;
	        this.index = index;
		this.constraint = constraint;
		this.foreignKeyTableName = foreignKeyTableName;
	}

	    public String getName() {
	        return name;
	    }

	    public String getType() {
	        return type;
	    }

	    public int getIndex() {
	        return index;
	    }

	    public String getConstraint(){ return constraint; }

	    public  String getForeignKeyTableName(){ return foreignKeyTableName; }
	
}
