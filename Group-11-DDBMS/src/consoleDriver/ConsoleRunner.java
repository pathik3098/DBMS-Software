package consoleDriver;

import CreateOperation.CreateDatabase;
import CreateOperation.CreateTable;
import CreateOperation.Use;
import DeleteOperation.DeleteParser;
import Login.UserLogin;
import SelectOperation.SelectParser;
import erd.ERDOperation;
import insertOperation.InsertOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
//import parser.CreateDatabaseRegex;
import parser.CreateTableRegex;
import parser.UseRegex;
import remoteDB.RemoteDBDownload;
import remoteDB.RemoteDBwriter;
import remoteDB.RemoteDbExecute;
import truncate.Truncate;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static CreateOperation.Use.currentDB;

public class ConsoleRunner {
	boolean result;
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

	public String tableLocation(String tablename)
	{
		String Tablelocation;
		String error = "table not found";
		try
		{
			FileInputStream file=new FileInputStream("src/LocalSite/TEST1/GDD.txt");
			Scanner sc = new Scanner(file);
			String values[];
			while(sc.hasNextLine())
			{
				String newLine = "";
				newLine = sc.nextLine();
				values = newLine.split("-");
				String table = values[0];
				String location = values[1];
				if(table.equals(tablename))
				{
					Tablelocation = location;
					return Tablelocation;
				}
			}
			file.close();
			sc.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return error;
	}

	public void initializeSystem() throws IOException {

		Scanner input = new Scanner(System.in);
		File file = new File("D:\\5408-Database-group-project\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\LocalSite\\TEST1\\input.txt");
		try {
			result = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
								CreateDatabase createDB= new CreateDatabase(query);
								createDB.execute();
						}

						if(splitQuery[1].toLowerCase().trim().equals("table"))
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
						String[] splitTableName = query.split(" ");
						String tableName = splitTableName[2];
						if(tableLocation(tableName).toLowerCase().equals("local"))
						{
							InsertOperation insert = new InsertOperation();
							//Use db = new Use();
							//String databaseName = db.getCurrentDB();
							String databaseName = "src/LocalSite/TEST1";
							insert.insertQueryParser(query, databaseName);
						}
						else if(tableLocation(tableName).toLowerCase().equals("remote"))
						{
							try {
								FileWriter myWriter = new FileWriter("D:\\5408-Database-group-project\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\LocalSite\\TEST1\\input.txt");
								myWriter.write(query);
								myWriter.close();
							}
							catch (IOException e) {
								System.out.println("An error occurred.");
								e.printStackTrace();
							}
							RemoteDBwriter write = new RemoteDBwriter();
							write.execute();
							RemoteDbExecute executeScript = new RemoteDbExecute();
							executeScript.execute();
							RemoteDBDownload get = new RemoteDBDownload();
							get.execute();
						}
						else
						{
							System.out.println(tableLocation(tableName));
						}
						break;

					case "select":
						log.info("Executing Select Query");
						SelectParser select = new SelectParser(query);
						List<String> rowsFetched = select.parseQuery(query);
						String TableName = select.fetchTableName(query);

						if(tableLocation(TableName).toLowerCase().equals("local"))
						{
							if (rowsFetched.size() != 0) {
								for (String record : rowsFetched) {
									System.out.println(record);
								}
							}
							else {
								System.out.println("No records available");
							}
						}
						else if(tableLocation(TableName).toLowerCase().equals("remote"))
						{
							try {
								FileWriter myWriter = new FileWriter("D:\\5408-Database-group-project\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\LocalSite\\TEST1\\input.txt");
								myWriter.write(query);
								myWriter.close();
							}
							catch (IOException e) {
								System.out.println("An error occurred.");
								e.printStackTrace();
							}
							RemoteDBwriter write = new RemoteDBwriter();
							write.execute();
							RemoteDbExecute executeScript = new RemoteDbExecute();
							executeScript.execute();
							RemoteDBDownload get = new RemoteDBDownload();
							get.execute();
						}
						else
						{
							System.out.println(tableLocation(TableName));
						}
						break;

					case "delete":
						String[] splitName = query.split(" ");
						String tabName = splitName[2];
						if(tableLocation(tabName).toLowerCase().equals("local"))
						{
							log.info("Executing Delete Query");
							DeleteParser delete = new DeleteParser();
							if(delete.parseQuery(query)) {
								System.out.println("Selected rows deleted");
							}
							else {
								log.error("Invalid Query Syntax");
								System.out.println("Invalid Query Syntax");
							}
						}
						else if(tableLocation(tabName).toLowerCase().equals("remote"))
						{
							try {
									FileWriter myWriter = new FileWriter("D:\\5408-Database-group-project\\csci-5408-w2021-group-11\\Group-11-DDBMS\\src\\LocalSite\\TEST1\\input.txt");
									myWriter.write(query);
									myWriter.close();
							}
							catch (IOException e) {
								System.out.println("An error occurred.");
								e.printStackTrace();
							}
							RemoteDBwriter write = new RemoteDBwriter();
							write.execute();
							RemoteDbExecute executeScript = new RemoteDbExecute();
							executeScript.execute();
							RemoteDBDownload get = new RemoteDBDownload();
							get.execute();
						}
						else
						{
							System.out.println(tableLocation(tabName));
						}
						break;

					case "truncate":
						log.info("Executing Truncate Query");
						Truncate truncate = new Truncate();
						Use dataBase = new Use();
						String dataBaseName = dataBase.getCurrentDB();
						truncate.executeTruncateQuery(query, dataBaseName);
						break;
					case "erd":
						ERDOperation erd = new ERDOperation();
						erd.erdQueryParser(query);
						break;
					default:
						return;
				}
			}
		}
	}
}
