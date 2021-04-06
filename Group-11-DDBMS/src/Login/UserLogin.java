package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserLogin {

    private String userName;
    private String password;
    String commandEntered;
    Scanner userInput = new Scanner(System.in);

    public void loginUser() {

        System.out.println("Please enter your credentials");
        System.out.print("Enter your username:");
        userName = userInput.next();
        System.out.print("Enter your password:");
        password = userInput.next();

        String line = "";
        String splitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Login/credentials.csv"));
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                userName = user[0];
                password = user[1];
                if(userName.equals(userName) && password.equals(password)) {
                    System.out.println("Login successful.");
                }
                else {
                    System.out.println("Incorrect Login");
                }
                break;
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
