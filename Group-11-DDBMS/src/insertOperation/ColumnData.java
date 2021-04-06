package insertOperation;

public class ColumnData {
	
	    private final String name;
	    private final String type;
	    private final int index;

	    public ColumnData(String name, String type,int index) {
	        this.name = name;
	        this.type = type;
	        this.index = index;
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
	
}
