import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RecruiterManager contains all methods associated with functionality of Recruiters for the SystemDriver.
 */
public class RecruiterManager extends UserManager {

    private final Scanner scanner = new Scanner(System.in);
    private Recruiter recruiter;

    /**
     * startSession() starts a new session in which Recruiters can choose their desired action.
     */
    @Override
    public void startSession() {
        String operation;
        String userName = authentication();
        if (userName!=null) {
            userIdentification(userName);
            if (recruiter != null && recruiter.getClass().getName().equals("Recruiter")) {
                recruiter.unreadMessageNotification();
                while (true) {

                    printOperations();
                    operation = scanner.next();
                    switch (operation) {
                        case "0":
                            jobsList();
                            break;
                        case "1":
                            addNewPost();
                            break;
                        case "2":
                            sendMessage();
                            break;
                        case "3":
                            showMessages();
                            break;
                        case "4":
                            return;
                    }
                }
            }
        }
        System.out.println("Authentication  error");
    }

    /**
     * showAllApplicantForJob() allows recruiters to view all applicants for a given job given applicationList.
     * @param applicationList
     */
    private void showAllApplicantForJob(List<Application> applicationList) {
        System.out.print("Applicants {");
        for (Application application : applicationList) {
            System.out.print("[" + application.getApplicantName() + "] ");
        }
        System.out.println("}");
    }

    /**
     * jobsList() allows recruiter to view all jobs.
     */
    private void jobsList() {
        System.out.println(DBManager.getInstance().getAllJobs());
        System.out.println("Enter job title");
        String title = scanner.next();
        Post job = DBManager.getInstance().getJob(title);
        if (job!=null) {
            String option;
            while (true){
                printOperationWithJobs();
                option=scanner.next();
                switch (option) {
                    case "0":
                        applicationList(job);
                        break;
                    case "1":
                        setAvailable(job);
                        break;
                    case "2":
                        showAllApplicantForJob(job.getApplications());
                        break;
                    case "3":
                        return;
                }
            }
        }
    }

    private void setAvailable(Post job) {
        job.setAvailable(false);
    }

    /**
     * Allows recruiter to push application to next round, reject application, or fill a position for given job.
     * @param job
     */
    private void applicationList(Post job) {
        System.out.println(job.getApplications());
        String operation;
        Application application = enterApplication(job);
        if (application!=null) {
            while (true) {
                printOperationsForApplication();
                operation = scanner.next();
                switch (operation) {
                    case "0":
                        application.nextRound();
                        System.out.println("New application round: " + application.returnProgress());
                        recruiter.sendMessage("you have been pushed to the next round of interview:"+
                                application.returnProgress(),application.getApplicantName());
                        break;
                    case "1":
                        application.setRejected();
                        job.getApplications().remove(application);
                        recruiter.sendMessage("your application:" + application.getPostTitle()
                                +"has been rejected.",application.getApplicantName() );
                        break;
                    case "2":
                        application.setFilled();
                        recruiter.sendMessage("your application:" + application.getPostTitle()
                                +"has been filled.",application.getApplicantName() );
                        break;
                    case "3":
                        System.out.println(DBManager.getInstance().returnInterviewersList());
                        System.out.println("Enter interviewer name: ");
                        operation = scanner.next();
                        for (User user : DBManager.getInstance().returnInterviewersList())
                            if (user.getUsername().equals(operation))application.addInterviewer((Interviewer) user);
                        break;
                    case "4":
                        return;
                }
            }
        }
    }

    /**
     * Allows recruiter to select application for a job.
     * @param job
     * @return
     */
    private Application enterApplication(Post job) {
        System.out.println("Enter applicant name: ");
        String userName = scanner.next();
        for (Application application : job.getApplications()) if (application.getApplicantName().equals(userName)) return application;
        return null;
    }

    private void printOperationsForApplication() {
        System.out.println("Options for application");
        System.out.println("'0' - go to the next round of interview");
        System.out.println("'1' - application status is rejected");
        System.out.println("'2' - application status filled");
        System.out.println("'3' - add interviewer");
        System.out.println("'4' - back");
    }

    private void addNewPost() {
        System.out.println("Enter job title");
        String title = scanner.next();
        System.out.println("Enter descriptions(several lines, at the end enter '0')");
        StringBuilder descriptions = new StringBuilder();
        String temp;
        while (true) {
            temp=scanner.next();
            if (temp.equals("0")) break;
            descriptions.append(temp);
        }
        System.out.println("Enter tags(several lines, at the end enter '0')");
        List<String> tags = enterTags();
        LocalDate date = enterDate();
        System.out.println("Enter days before closing vacancy");
        int daysBeforeClosing = Integer.parseInt(scanner.next());
        System.out.println("Enter positions number");
        int positions = Integer.parseInt(scanner.next());
        List<String> rounds = enterInterviewRounds();
        Post post = new Post(positions,title,descriptions.toString(),date,daysBeforeClosing);
        post.setTags(tags);
        post.setRounds(rounds);
        DBManager.getInstance().addJob(post);
    }

    /**
     * Returns a new list of interview rounds based on prompt.
     * @return
     */
    private List<String> enterInterviewRounds() {
        List<String> rounds = new ArrayList<>();
        System.out.println("Enter interview rounds in one word (several lines, at the end enter '0')");
        System.out.println("Final round must be 'Rejected'");
        rounds.add("Filled");
        String temp;
        while (true) {
            temp = scanner.next();
            if (temp.equals("0")) break;
            rounds.add(temp);}
        return rounds;
    }

    /**
     * Returns a new list of tags based on prompt.
     * @return
     */
    private List<String> enterTags() {
        List<String> tags = new ArrayList<>();
        String temp;
        while (true) {
            temp = scanner.next();
            if (temp.equals("0")) break;
            tags.add(temp);
        }
        return tags;
    }

    private void printOperationWithJobs() {
        System.out.println("'0' - applications list");
        System.out.println("'1' - set this job not available");
        System.out.println("'2' - show list of all applicants for a specific job posting");
        System.out.println("'3' - back");
    }

    private LocalDate enterDate() {
        System.out.println("Enter Posting Date(year)");
        int year = Integer.parseInt(scanner.next());
        System.out.println("Enter Posting Date(month)");
        int month = Integer.parseInt(scanner.next());
        System.out.println("Enter Posting Date(day of month)");
        int days = Integer.parseInt(scanner.next());
        return LocalDate.of(year,month,days);
    }

    private void sendMessage() {
        System.out.println("Enter the name of the recipient: ");
        String username = scanner.next();
        System.out.println("Enter message: ");
        String message = scanner.next();
        message = message + scanner.nextLine();
        recruiter.sendMessage(message,username);
    }

    private void showMessages() {
        System.out.println("-----unread messages-----");
        System.out.println(recruiter.getUnreadMessages());
        System.out.println(("-----history messages-----"));
        System.out.println(recruiter.getReadMessages());
        recruiter.setUnreadToRead();
    }

    private void printOperations() {
        System.out.println("'0' - jobs list");
        System.out.println("'1' - add new post");
        System.out.println("'2' - send message");
        System.out.println("'3' - show message list");
        System.out.println("'4' - logout");
    }

    /**
     * Identifiers whether system creates a new Recruiter or Recruiter already exists in the system by username.
     * @param userName
     */
    private void userIdentification(String userName) {
        User user = DBManager.getInstance().getUser(userName);
        if (user!=null) {
            if (!user.getClass().getName().equals("Recruiter")) return;
            recruiter = (Recruiter) user;
        }
        else recruiter = registration(userName);
    }

    /**
     * Registers a new Recruiter with userName.
     * @param userName
     * @return
     */
    private Recruiter registration(String userName) {
        System.out.println("Enter password: ");
        recruiter = new Recruiter(userName, scanner.next());
        System.out.println("Enter your company name: ");
        String company = scanner.next();
        recruiter.setCompany(new Company(company));
        DBManager.getInstance().addUser(recruiter);
        return recruiter;
    }
}