import java.util.Scanner;

public class SystemDriver {

    private static UserManager applicantManager = new ApplicantManager();
    private static UserManager interviewerManager = new InterviewerManager();
    private  static UserManager recruiterManager = new RecruiterManager();
    private static UserManager refereeManager = new RefereeManager();

    public static void main(String[] args) {

        DBManager.getInstance().readAllUsersAndJobs();
        Scanner scanner = new Scanner(System.in);
        String option;

        while (true) {
            printOptions();
            option = scanner.next();
            switch (option) {
                case "0":
                    applicantManager.startSession();
                    break;
                case "1":
                    interviewerManager.startSession();
                    break;
                case "2":
                    recruiterManager.startSession();
                    break;
                case "3":
                    refereeManager.startSession();
                    break;
                case "4":
                    DBManager.getInstance().writeAllUsersAndJobs();
                    System.exit(0);
                    break;
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void printOptions() {
        System.out.println("---- Options ----");
        System.out.println("'0' - I'm applicant");
        System.out.println("'1' - I'm interviewer");
        System.out.println("'2' - I'm recruiter");
        System.out.println("'3' - I'm referee");
        System.out.println("'4' - Exit");
    }
}