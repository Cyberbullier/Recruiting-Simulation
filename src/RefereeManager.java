import java.util.Scanner;

public class RefereeManager extends UserManager {

    private Referee referee;
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public void startSession() {

        String operation;
        String userName = authentication();
        if (userName!=null) {
            userIdentification(userName);
            if (referee!=null&&referee.getClass().getName().equals("Referee"));
            while (true) {
                printOperations();
                operation=scanner.next();
                switch (operation) {
                    case "0":
                        addReferenceLetter();
                        break;
                    case "1":
                        jobsList();
                        break;
                    case "2":
                        return;
                }
            }
        }
        System.out.println("Authentication  error");
    }

    private void jobsList() {
        System.out.println(DBManager.getInstance().getAllJobs());
    }

    private void addReferenceLetter() {
        System.out.println("Enter applicant name: ");
        String applicantName =scanner.next();
        if (DBManager.getInstance().getUser(applicantName)!=null) {
            System.out.println("Please place your file in the \"Documents\" folder and enter the file name (for example CV1.docx): ");
            String fileName =  scanner.next();
            if (DocsAccess.getInstance().existDoc(fileName)) {
                ((Applicant)DBManager.getInstance().getUser(applicantName)).addDocument(new Document(fileName,Docs.RECOMMENDATION));
            }
            else System.out.println("Invalid doc name");
        }
        else System.out.println("Invalid applicant name");
    }

    private void printOperations() {
        System.out.println("'0' - add reference latter");
        System.out.println("'1' - jobs list");
        System.out.println("'2' - logout");
    }

    private void userIdentification(String userName) {
        User user = DBManager.getInstance().getUser(userName);
        if (user!=null){
            if (!user.getClass().getName().equals("Referee")) return;
            referee = (Referee) user;
        }
        else referee=registration(userName);
    }

    private Referee registration(String userName) {
        System.out.println("Enter password: ");
        referee = new Referee(userName,scanner.next());
        DBManager.getInstance().addUser(referee);
        return referee;
    }
}