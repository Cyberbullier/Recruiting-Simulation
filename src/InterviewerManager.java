import java.util.List;
import java.util.Scanner;

/**
 * InterviewerManager contains all methods associated with functionality of interviewer's for the SystemDriver.
 */
public class InterviewerManager extends UserManager {

    //https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
    private final Scanner scanner = new Scanner(System.in);
    private Interviewer interviewer;

    /**
     * startSession() starts a new session in which Interviewer's can choose their desired action.
     */
    @Override
    public void startSession() {
        String operation;
        String userName = authentication();
        if (userName!=null) {
            userIdentification(userName);
            if (interviewer != null && interviewer.getClass().getName().equals("Interviewer")) {
                while (true) {
                    printOperations();
                    operation = scanner.next();
                    switch (operation) {
                        case "0":
                            toNextStep();
                            break;
                        case "1":
                            return;

                    }
                }
            }
        }
        System.out.println("Authentication  error");
    }

    /**
     * toNextStep() allows interviewer to move Applicant onto the next interview round.
     */
    private void toNextStep() {
        System.out.println("Jobs list: " + DBManager.getInstance().getAllJobs());
        System.out.println("Enter job title or '0' for exit");
        String option;
        option = scanner.next();
        if (option.equals("0")){ return;}
        choiceApplication(option);
    }

    /**
     * choiceApplication() allows interviewer to select an application from a job by jobTitle.
     * @param jobTitle
     */
    private void choiceApplication(String jobTitle) {
        try {
            System.out.println("Enter applicant name: ");
            String applicantTitle = scanner.next();
            List<Application> applications = DBManager.getInstance().getJob(jobTitle).getApplications();
            for (Application application : applications) {
                if (application.getApplicantName().equals(applicantTitle)) {
                    application.nextRound();
                    System.out.println("New interview round is: " + application.returnProgress());
                    interviewer.sendMessage("you have been sent to the next round of interview:" +
                            application.returnProgress(), application.getApplicantName());
                }
            }
        }
        catch (NullPointerException ex) {
            System.out.println("Wrong job Title");
        }

    }

    private void printOperations() {
        System.out.println("'0' - send the applicant to next step");
        System.out.println("'1' - logout");

    }

    /**
     * Identifiers whether system creates a new interviewer or interviewer already exists in the system by username.
     * @param userName
     */
    private void userIdentification(String userName) {
        User user = DBManager.getInstance().getUser(userName);
        if (user!=null) {
            if (!user.getClass().getName().equals("Interviewer")) return;
            interviewer = (Interviewer) user;
        }
        else interviewer = registration(userName);
    }

    /**
     * Registers a new Interviewer with userName.
     * @param userName
     * @return
     */
    private Interviewer registration(String userName) {
        System.out.println("Enter password: ");
        interviewer = new Interviewer(userName, scanner.next());
        DBManager.getInstance().addUser(interviewer);
        return interviewer;
    }
}
