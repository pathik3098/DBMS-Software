package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserLogin {

    private String userName;
    private String password;
    Scanner userInput = new Scanner(System.in);

    public boolean loginUser() {

        System.out.println("Please enter your credentials");
        System.out.print("Enter your username:");
        userName = userInput.next();
        System.out.print("Enter your password:");
        password = userInput.next();

        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Login/credentials.csv"));
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                String userId = user[0];
                String userPassword = user[1];
                if(userName.equals(userId) && password.equals(userPassword)) {
                    System.out.println("Login successful.");
                    return true;
                }
                else {
                    continue;
                }
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
