package parser;

import java.util.Scanner;

import insertOperation.InsertOperation;

public class Main {
	
	public static void main(String[] args) {
		
		InsertOperation insert = new InsertOperation();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter insert QUery : ");
		String query = sc.nextLine();
		insert.insertQueryParser(query, "hello");
	}

}
