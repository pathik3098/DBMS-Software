package consoleDriver;

import CreateOperation.CreateDatabase;
import CreateOperation.CreateTable;
import CreateOperation.Use;
import DeleteOperation.DeleteParser;
import Login.UserLogin;
import SelectOperation.SelectParser;
import insertOperation.InsertOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import parser.CreateDatabaseRegex;
import parser.CreateTableRegex;
import parser.UseRegex;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleRunner {

	public final static String QueryExit = "QUIT";
	Scanner userInput = new Scanner(System.in);
	private static Logger log = LogManager.getLogger(ConsoleRunner.class);
	public void run() throws IOException {

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
				if (login.loginUser()) {
					log.info("Logged in successfully");
					initializeSystem();
				}
				else {
					log.error("login Failed");
					System.out.println("Invalid Credentials");
					return;
				}
			} else if (commandEntered.equalsIgnoreCase("quit")) {
				System.out.println("Exiting the application.");
				return;
			} else {
				log.warn("Bad Input");
				System.out.println("Bad Command, Please enter a valid input - 1 or quit");
			}
		} while (!commandEntered.equalsIgnoreCase(QueryExit));
	}

	public void initializeSystem() throws IOException {

		Scanner input = new Scanner(System.in);
		while (true) {
			String[] splitQuery;
			System.out.print("query>");
			String query = input.nextLine();
			if (query.equalsIgnoreCase(QueryExit)) {
				return;
			}
			else {
				splitQuery = query.split(" ");
				String queryOperation = splitQuery[0];
				switch (queryOperation.toLowerCase().trim()) {
					case "use":
						log.info("Executing Use Query");
						UseRegex useRe = new UseRegex();
						boolean valid = useRe.checkUseQuery(query);
						if (valid) {
							Use useDB = new Use(query);
							useDB.execute();
						} else {
							System.out.println("Invalid Query, PLease try again ");
						}
						break;
					case "create":
						log.info("Executing Create Query");
						if(splitQuery[1].toLowerCase().trim().equals("database"))
						{
							CreateDatabaseRegex DBregex = new CreateDatabaseRegex();
							boolean validQuery = DBregex.checkDBQuery(query);
							if(validQuery)
							{
								CreateDatabase createDB= new CreateDatabase(query);
								createDB.execute();
							}
							else
							{
								System.out.println("Invalid Query, PLease try again ");
							}
						}

						else if(splitQuery[1].toLowerCase(Locale.ROOT).trim().equals("table"))
						{
							CreateTableRegex tableRegex = new CreateTableRegex();
							boolean validQuery = tableRegex.checkTableRegex(query);
							if (validQuery) {
								CreateTable create = new CreateTable(query);
								create.execute();
							} else {
								System.out.println("Invalid Query, PLease try again ");
							}
						}
						break;
					case "insert":
						log.info("Executing Insert Query");
						InsertOperation insert = new InsertOperation();
						Use db = new Use();
						String databaseName = db.getCurrentDB();
						insert.insertQueryParser(query, databaseName);
						break;
					case "select":
						log.info("Executing Select Query");
						SelectParser select = new SelectParser(query);
						List<String> rowsFetched = select.parseQuery(query);
						if (rowsFetched.size() != 0) {
							for (String record : rowsFetched) {
								System.out.println(record);
							}
						}
						else {
							System.out.println("No records available");
						}

						break;
					case "delete":
						log.info("Executing Delete Query");
						DeleteParser delete = new DeleteParser();
						if(delete.parseQuery(query)) {
							System.out.println("Selected rows deleted");
						}
						else {
							log.error("Invalid Query Syntax");
							System.out.println("Invalid Query Syntax");
						}
						break;
					default:
						return;
				}
			}
		}
	}
}
