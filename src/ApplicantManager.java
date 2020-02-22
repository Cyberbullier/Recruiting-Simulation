import java.util.*;

/**
 * ApplicantManager contains all methods associated with functionality of Applicant's for the SystemDriver.
 */

public class ApplicantManager extends UserManager {

    private final Scanner scanner = new Scanner(System.in);
    private Applicant applicant;

    //Think from the user's (Applicant) perspective. All the user will have some sort of this method.

    /**
     * startSession() starts a new session in which applicant's can choose their desired action.
     */
    @Override
    public void startSession() {
        String operation; //What user will input
        String userName = authentication();
        if (userName!=null) {
            userIdentification(userName);
            if (applicant != null && applicant.getClass().getName().equals("Applicant")) {
                applicant.unreadMessageNotification();
                while (true) {
                    //User(Applicant) will see the list of operation and based on that he will input accordingly.Add the methods later.
                    printOperations();
                    operation = scanner.next();
                    switch (operation) {
                        case "0":
                            jobsList();
                            break;
                        case "1":
                            viewInfo();
                            break;
                        case "2":
                            addDocument();
                            break;
                        case "3":
                            sendMessage();
                            break;
                        case "4":
                            showMessageList();
                            break;
                        case "5":
                            showDocuments();
                            break;
                        case "6":
                            searchJob();
                            break;
                        case "7":
                            DBManager.getInstance().addUser(applicant);
                            return;
                    }
                }
            }
        }
        System.out.println("Authentication  error");
    }

    //Extra feature for applicant. So applicant can search through skills.

    /**
     * searchJob() allows applicant to be able to search for jobs.
     */
    private void searchJob() {
        System.out.println("Enter tags for jobs search. To complete the addition of tags, enter '0'");
        String operation;
        List<String> tags = new ArrayList<>();
        while (true) {
            operation = scanner.next();
            if (operation.equals("0")) break;
            tags.add(operation);
        }
        jobsSearch(tags);
    }

    /**
     * jobsSearch(tags) allows applicant to search for jobs by entering a list of tags.
     * @param tags
     */

    private void jobsSearch(List<String> tags) {
        Set<String> jobTitles = new TreeSet<>();
        for (Post post : DBManager.getInstance().getAllJobs()) {
            for (String tag : tags) {
                if (post.getTags().contains(tag)) jobTitles.add(post.getTitle());
            }
        }
        System.out.print("Jobs with tags: ");
        for (String job : jobTitles) {
            System.out.print(job + " ");
        }
        System.out.println(".");
    }

    /**
     * showDocuments() shows the Applicant's current documents holding CV and Resume.
     */
    private void showDocuments() {
        System.out.println(applicant.getDocuments());
    }

    /**
     * showMessageList() allows Applicant to view current message list.
     */
    private void showMessageList() {
        System.out.println("-----unread messages-----");
        System.out.println(applicant.getUnreadMessages());
        System.out.println(("-----history messages-----"));
        System.out.println(applicant.getReadMessages());
        applicant.setUnreadToRead();
    }



    /**
     * sendMessage() sends a message in the form of a string to a given user.
     */
    private void sendMessage() {
        System.out.println("Enter the name of the recipient ");
        String username = scanner.next();
        System.out.println("Enter message: ");
        String message = scanner.next();
        message = message + scanner.nextLine();
        applicant.sendMessage(message,username);
    }

    /**
     * addDocument() allows Applicant to add a new document which holds Applicant's Resume, CV, Cover letter.
     */
    private void addDocument() {
        System.out.println("Please place your file in the \"Documents\" folder and enter the file name (for example CV1.docx): ");
        String fileName =  scanner.next();
        System.out.println("Enter doc type: ");
        System.out.println("'0' - CV");
        System.out.println("'1' - COVER_LETTER");
        System.out.println("'2' - RECOMMENDATION");
        //Based on applicant user's input we save accordingly
        switch (scanner.next()) {
            case "0":
                saveDoc(Docs.CV, fileName);
                break;
            case "1":
                saveDoc(Docs.COVER_LETTER, fileName);
                break;
            case "2":
                saveDoc(Docs.RECOMMENDATION, fileName);
                break;
        }
    }

    /**
     * saveDoc() allows Applicant to save new doc with docs and filename.
     * @param docs
     * @param fileName
     */
    private void saveDoc(Docs docs, String fileName) {
        if (DocsAccess.getInstance().existDoc(fileName))
            applicant.addDocument(new Document(fileName,docs));
        else System.out.println("File not found");

    }

    private void viewInfo() {
        System.out.println(applicant);
    }

    /**
     * jobsList() allows Applicant to select a job title and add an new application.
     */

    private void jobsList() {
        System.out.println(DBManager.getInstance().getAllJobs());
        System.out.println("Enter job title: ");
        Post post = DBManager.getInstance().getJob(scanner.next());
        List<Document> documents = new ArrayList<>();
        String option;
        if (post!=null) {
            Application application;
            System.out.println("For add application enter the name of the documents one by one (for example, '1.txt.'), To complete the addition of documents, enter '0'");
            while (true) {
                System.out.println("Enter filename or '0'");
                option = scanner.next();
                if (option.equals("0")) break; //0 as a submit button
                documents.add(new Document(option,enterDocumentType()));
            }
            application = new Application(post.getTitle(),applicant.getUsername(), post.getRounds());
            application.addDocuments(documents);
            post.addApplication(application);
        }
        else System.out.println("Invalid job title");
    }

    private Docs enterDocumentType() {
        System.out.println("Enter doc type: ");
        System.out.println("'0' - CV");
        System.out.println("'1' - COVER_LETTER");
        System.out.println("'2' - RECOMMENDATION");
        String type=scanner.next();
        switch (type) {
            case "0":
                return Docs.CV;
            case "1":
                return Docs.COVER_LETTER;
            case "2":
                return Docs.RECOMMENDATION;
        }
        return null;
    }

    private void printOperations() {
        System.out.println("'0' - jobs list");
        System.out.println("'1' - view info");
        System.out.println("'2' - add document");
        System.out.println("'3' - send message");
        System.out.println("'4' - show message list");
        System.out.println("'5' - show documents");
        System.out.println("'6' - jobs search by tags");
        System.out.println("'7' - logout");
    }

    /**
     * Identifiers whether system creates a new applicant or applicant already exists in the system by username.
     * @param userName
     */
    private void userIdentification(String userName) {
        User user = DBManager.getInstance().getUser(userName);
        if (user!=null){
            if (!user.getClass().getName().equals("Applicant")) return;
            applicant = (Applicant) user;
        }
        else {applicant = registration(userName);}
    }

    /**
     * Registers a new Applicant with userName.
     * @param userName
     * @return Applicant
     */
    private Applicant registration(String userName) {
        System.out.println("Enter password: ");
        applicant = new Applicant(userName,scanner.next());
        DBManager.getInstance().addUser(applicant);
        return applicant;
    }
}