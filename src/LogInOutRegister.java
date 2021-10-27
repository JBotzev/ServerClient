import java.io.IOException;
import java.util.Scanner;

public class LogInOutRegister {

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.print("Register(R), log in(I) or log out(O):");
        String action = in.nextLine();
        //System.out.println("Action: " + action);

        if(action.equals("R")){
            register();
        }
        else if(action.equals("I")){
            logIn();
        }
        else{
            logOut();
        }
    }

    public static void register() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();

        Client client = new Client(username, password, "r");
        client.run();
    }

    public static void logIn() throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();
        Client client = new Client(username, password, "i");
        client.run();

    }

    public static void logOut(){
        System.out.println("Successfully logged out!");
        System.exit(0);
    }
}
