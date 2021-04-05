package com.group11;

public class Main {
	
	public static void main(String[] args) {
		
		InsertRegex insert = new InsertRegex();
		String query = "INSERT into first VALUES (1, 'abc', 34))";
		insert.insertQueryParser(query, "hello");
	}

}
