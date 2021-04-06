package consoleDriver;

import DeleteOperation.DeleteParser;
import Login.UserLogin;
import SelectOperation.SelectParser;

import java.util.List;
import java.util.Scanner;

public class consoleRunner {

	//public final static String QueryRequest ="query> ";
	public final static String QueryExit = "QUIT";
	Scanner userInput = new Scanner(System.in);

	public void run() {

		String commandEntered;
		String quitCommand = "quit";
		System.out.println("Welcome");
		System.out.println("1. To login ");
		System.out.println("quit. To exit the application");
		System.out.println("Choose 1 or quit");
		do {
			commandEntered = userInput.next();
			if (commandEntered.equalsIgnoreCase("1")) {
				UserLogin login = new UserLogin();
				login.loginUser();
				initializeSystem();
			} else if (commandEntered.equalsIgnoreCase("quit")) {
				System.out.println("Exiting the application.");
				return;
			} else {
				System.out.println("Bad Command, Please enter a valid input - 1 or quit");
			}
		} while (!commandEntered.equalsIgnoreCase(QueryExit));
	}

	public void initializeSystem() {

		Scanner input = new Scanner(System.in);
		do {
			String[] splitQuery;
			System.out.print("query>");
			String query = input.nextLine();
			System.out.println("entered query:" + query);
			if (query.equalsIgnoreCase(QueryExit)) {
				return;
			}
			else {
				splitQuery = query.split(" ");
				String queryOperation = splitQuery[0];
				switch (queryOperation.toLowerCase().trim()) {
					case "use":
						//call to use operation
					case "create":
						//call to create operation
					case "insert":
						//call to insert
					case "select":
						SelectParser select = new SelectParser(query);
						List<String> rowsFetched = select.parseQuery(query);
						if (rowsFetched.size() == 0) {
							System.out.println("No records returned");
						} else {
							for (String record : rowsFetched) {
								System.out.println(record);
							}
						}
					case "delete":
						DeleteParser delete = new DeleteParser();
						delete.parseQuery(query);
						System.out.println("Selected rows deleted");
					default:
						return;
				}
			}
		} while (!input.equals(QueryExit));
	}
}
