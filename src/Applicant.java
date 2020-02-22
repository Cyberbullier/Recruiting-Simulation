import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Applicant extends User {

    private List<Document> documents;
    private LocalDate creationDate;
    private ApplicantStatus status;

    public Applicant(String username, String password) {
        super(username, password);
        documents = new ArrayList<>();
        status=ApplicantStatus.FREE;
        creationDate=LocalDate.now();
    }

    /**
     * Adds a new document which holds filename and type. file should contain important documents such as
     * resume and CV.
     * @param document
     */

    public void addDocument(Document document) {
        documents.add(document);
     }

    public List<Document> getDocuments() {
        return documents;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public ApplicantStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicantStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString()+
                "documents=" + documents +
                ", creationDate=" + creationDate +
                ", status=" + status +
                '}';
    }


}
