import java.io.Serializable;

/**
 * Document holds filename, type where file should contain important information relevant to applicants such as
 * resume
 * cv
 * cover letter.
 */

public class Document implements Serializable {

    private String fileName;
    private Docs type;

    public Document(String fileName, Docs type) {
        this.fileName = fileName;
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Docs getType() {
        return type;
    }

    public void setType(Docs type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Document{" +
                ", fileName='" + fileName + '\'' +
                ", type=" + type +
                '}';
    }
}
