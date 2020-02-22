import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Application  implements Serializable {

    private String postTitle;
    private String applicantName;
    private int interviewRound;
    private List<Document> documents;
    private List<Interviewer> interviewers;
    private List<String> rounds;


    public Application(String postTitle, String applicantName, List<String> rounds) {
        this.postTitle = postTitle;
        this.applicantName = applicantName;
        documents = new ArrayList<>();
        interviewers = new ArrayList<>();
        interviewRound=1;
        this.rounds=rounds;

    }

    /**
     * adds new interview step in the form of a string(round) to list of rounds for this application.
     * @param round
     */
    public void addRound(String round) {
        rounds.add(round);
    }

    /**
     * returns all interview rounds for this application.
     * @return
     */
    public List<String> getRounds() {
        return rounds;
    }

    /**
     * Sets this application's list of interview rounds to rounds.
     * @param rounds
     */
    public void setRounds(List<String> rounds) {
        this.rounds = rounds;
    }

    /**
     * adds interviewer to list of interviewers for this application.
     * @param interviewer
     */
    public void addInterviewer(Interviewer interviewer) {
        interviewers.add(interviewer);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * adds documents containing information such as CV, Resume, Cover letter for this application.
     * @param documents
     */

    public void addDocuments(List<Document> documents) {
        documents.addAll(documents);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public String getPostTitle() {
        return postTitle;
    }

    /**
     * nextRound() moves this application into the next round.
     */

    public void nextRound() {
        if (interviewRound<rounds.size()-1) {
            interviewRound++;
        }
        if (interviewRound==(rounds.size()-1)) {
            ((Applicant)DBManager.getInstance().getUser(applicantName)).setStatus(ApplicantStatus.HIRED);
        }
    }

    public void setFilled() {interviewRound=rounds.size();}

    /**
     * setRejected() sets this applications interviewRound to 0 and makes this application no longer active.
     */
    public void setRejected() {
        interviewRound=0;
    }

    public int getInterviewRound() {
        return interviewRound;
    }

    public String returnProgress(){
        return rounds.get(interviewRound);
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Override
    public String toString() {
        return "Application{" +
                "postTitle='" + postTitle + '\'' +
                ", applicantName='" + applicantName + '\'' +
                ", interviewRound=" + interviewRound +
                ", documents=" + documents +
                ", interviewers=" + interviewers +
                '}';
    }
}