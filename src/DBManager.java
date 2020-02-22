import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Save changes to the lists to the database after each update in the program (setAllUsers method)
 * This class is about reading and writing.
 * To understand this class take a look at the oracle doc
 * https://docs.oracle.com/javase/8/docs/api/java/io/ObjectInputStream.html
 * https://docs.oracle.com/javase/8/docs/api/java/io/ObjectOutputStream.html
 */

public class DBManager {

    //Singleton- making constructor private so that class cant be instantiated. See tutorials point for details.
    private DBManager() {}

    private static DBManager instance = new DBManager();

    public static DBManager getInstance() {
        return instance;
    }

    private   List<User> allUsers = new ArrayList<>();
    private List<Post> allJobs = new ArrayList<>();
    //Constants
    private static final String USERS_PATH = "allUsers/";
    private static final String JOBS_PATH = "allJobs/";


    public void readAllUsersAndJobs() {
        if (new File(USERS_PATH).exists())
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_PATH));
            ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(JOBS_PATH))) {
                setAllUsers((List<User>) in.readObject());
                setAllJobs((List<Post>) in2.readObject());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void writeObject(String path, Object o) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            new File(path).delete();
            out.writeObject(o);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
        writeObject(USERS_PATH, allUsers);
    }

    public List<Post> getAllJobs() {
        return allJobs;
    }

    public void setAllJobs(List<Post> allJobs) {
        this.allJobs = allJobs;
        writeObject(JOBS_PATH, allJobs);
    }

    public void addJob(Post post) {
        allJobs.add(post);
        writeObject(JOBS_PATH, allJobs);
    }

    public List<User> returnInterviewersList() {
        List<User> users = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getClass().getName().equals("Interviewer")) users.add(user);
        }
        return users;
    }


    public void writeAllUsersAndJobs() {

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_PATH));
            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(JOBS_PATH))) {

            new File(USERS_PATH).delete();
            new File(JOBS_PATH).delete();
            if (allUsers.size()>0) out.writeObject(allUsers);
            if (allJobs.size()>0) out2.writeObject(allJobs);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void addUser(User user) {
        allUsers.add(user);
        writeObject(USERS_PATH,allUsers);
    }
    public Post getJob(String title) {
        for (Post post : allJobs) {
            if (post.getTitle().equals(title)) return post;
        }
        return null;
    }

    public User getUser(String userName) { // null if user not exist
        for (User user : allUsers) {
            if (user.getUsername().equals(userName))
                return user;
        }
        return null;
    }
}