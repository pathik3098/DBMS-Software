package parser;

import insertOperation.InsertOperation;

public class Main {
	
	public static void main(String[] args) {
		
		InsertOperation insert = new InsertOperation();
		String query = "INSERT into first VALUES (1, 'abc', 34))";
		insert.insertQueryParser(query, "hello");
	}

}
