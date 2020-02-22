import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

//https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html

/**
 * Post represents a job posting with
 * jobtitle, title, description, company, applications, list of rounds an list of tags.
 */

public class Post implements Serializable {

    private int numPositions;
    private String title;
    private String description;
    private boolean isAvailable;
    private LocalDate postingDate;
    private LocalDate finishDate;
    private List<Application> applications;
    private List<String> tags;
    private List<String> rounds;


    public Post(int numPositions, String title, String description, LocalDate postingDate, int daysAvailable) {
        this.numPositions = numPositions;
        this.title = title;
        this.description = description;
        this.postingDate = postingDate;
        this.finishDate = setFinishDate(daysAvailable);
        isAvailable =true;
        applications = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * returns a date in the form of local date for which the post expires given daysAvailable.
     * @param daysAvailable
     * @return localdate for last date post is available
     */
    private LocalDate setFinishDate(int daysAvailable) {
        //From the link, LocalDate a = LocalDate.of(2012, 6, 30)
        LocalDate tempDate = LocalDate.of(postingDate.getYear(),postingDate.getMonthValue(),postingDate.getDayOfMonth());
        tempDate=tempDate.plusDays(daysAvailable);
        return tempDate;
    }

    /**
     * adds a tag to the post's list of tags for user to search from.
     * @param tag
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * sets list of tags to tags.
     * @param tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * returns list of strings in the form of interview rounds in sequential order.
     * @return list of rounds.
     */
    public List<String> getRounds() {
        return rounds;
    }

    /**
     * sets rounds of interviews to given list of rounds.
     * @param rounds
     */
    public void setRounds(List<String> rounds) {
        this.rounds = rounds;
    }

    public List<Application> getApplications() {
        return applications;
    }

    /**
     * sets this post's list of applications to applications.
     * @param applications
     */
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public int getNumPositions() {
        return this.numPositions;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * returns true iff post is still available and active.
     * @return boolean
     */
    public boolean isAvailable() {
        //Checking the conditions
        return isAvailable &&
                applications.size() <= numPositions &&
                (postingDate.isBefore(LocalDate.now()) && LocalDate.now().isBefore(finishDate));
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getTitle() {
        return this.title;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Post{" +
                "numPositions=" + numPositions +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isAvailable=" + isAvailable +
                ", postingDate=" + postingDate +
                ", finishDate=" + finishDate +
                ", applications=" + applications +
                ", tags=" + tags +
                ", rounds=" + rounds +
                '}';
    }
}