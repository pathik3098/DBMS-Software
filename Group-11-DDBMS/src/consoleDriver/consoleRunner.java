package consoleDriver;

import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException; 
import java.util.Scanner;

public class consoleRunner {
	
	String name;
    String pwd;
    public final static String QueryRequest ="query> ";
	public final static String QueryExit ="EXIT";
	
	// Method to run via Console
	public void run() {

		// Initializing variables
		String commandEntered;
		Scanner userInput = new Scanner(System.in);
		String quitCommand = "quit";
		String adminUsername;
		String adminPassword;
		
		System.out.println("Welcome");
		System.out.println("1. To login ");
		System.out.println("quit. To exit the application");
		System.out.println("Choose 1 or quit");
		
		do {
			// First call the login function and login as customer or administrator
			commandEntered = userInput.next();
			if(commandEntered.equalsIgnoreCase("1")) {
				//logic for user authentication
				System.out.println("Please enter your credentials");
		        System.out.println("Enter your username:");
		        adminUsername = userInput.next();
		        System.out.println("Enter your password:");
		        adminPassword = userInput.next();
		
		    	String line = "";
		    	String splitBy = ",";
		    	
		    	try   
		    	{  
		    	BufferedReader br = new BufferedReader(new FileReader("credentials.csv"));  
		    	while ((line = br.readLine()) != null)   
		    	{  
		    	String[] user = line.split(splitBy);
		    	name = user[0];
		    	pwd = user[1];
		    	if(adminUsername.equals(name) && adminPassword.equals(pwd)) {
					System.out.println("Login was successful.");
					initializeSystem();
				   }
		    	else {
		    		System.out.println("Incorrect Login");
		    	}
		    	break;
		    	}
		    	br.close();
		    	}   
		    	catch (IOException e)   
		    	{  
		    	System.out.println(e.getMessage()); 
		    	}    
			}
			else if(commandEntered.equalsIgnoreCase("quit")) {
				System.out.println("Exiting the application.");
			}
			else {
				System.out.println("Bad Command, Please enter a valid input - 1 or quit");
			}
	}while(!commandEntered.equalsIgnoreCase(quitCommand));
	userInput.close();
	}

	public void initializeSystem() {

		System.out.println(QueryRequest);
		String query = "";
		do {
			Scanner scanner = new Scanner(System.in);
			query =scanner.nextLine();
			if (query.equalsIgnoreCase(QueryExit)) {
				return;
			}
		}while (!query.equalsIgnoreCase(QueryExit));

	}

}
