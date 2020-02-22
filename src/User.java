import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * User contains:
 * List<Message> unreadMessages
 * List<Message> readMessages
 * String username
 * String password
 */
public abstract class User implements Serializable {

    private List<Message> unreadMessage;
    private List<Message> readMessage;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        unreadMessage = new ArrayList<>();
        readMessage = new ArrayList<>();

    }

    /**
     * sets all unreadmessages to read
     */

    public void setUnreadToRead(){
        readMessage.addAll(unreadMessage);
        unreadMessage.clear();
    }

    public void sendMessage(String message, String username) {
        User user = DBManager.getInstance().getUser(username);
        if (user != null) {
            user.unreadMessage.add(new Message(this.username, message));
        }
        else System.out.println("Invalid user Name");
    }

    public List<Message> getUnreadMessages() {
        return unreadMessage;
    }

    public List<Message> getReadMessages() {
        return readMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sends notification to User if user has an unread message.
     */
    public void unreadMessageNotification(){
        if (unreadMessage != null) {
            System.out.println("you have" + " " + unreadMessage.size() + " " + "unread messages");
        }
        else{
            System.out.println("you have 0 unread messages");
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "unread messages=" + unreadMessage +
                ", username='" + username + '\'' +
                '}';
    }
}