import java.util.Scanner;

/**
 * UserManager is an abstract class which
 * contains all methods associated with functionality of Users's for the SystemDriver.
 */

public abstract class UserManager {


    abstract void startSession();


    String authentication() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user name");
        String userName = scanner.next();
        User user = DBManager.getInstance().getUser(userName);
        if (user!=null) {
            System.out.println("Enter password ");
            if (scanner.next().equals(user.getPassword())) return user.getUsername();
            else {
                System.out.println("Wrong password!");
                return null;
            }
        }
        else return registration(userName,scanner);
    }
    private String registration(String userName, Scanner scanner) {
        System.out.println("username not found");
        String option;
        System.out.println("enter option: " +
                "\n0 - re-enter name" +
                "\n1 - create new user");
        option = scanner.next();
        if (option.equals("0")) return authentication(); //if user re-enter name
            return userName;
    }
}