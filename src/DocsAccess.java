import java.io.File;

/**
 * Allows system to access docs for a specific instance
 */
public class DocsAccess {


    private static DocsAccess docsAccess = new DocsAccess();
    // Singleton Design. Private constructor to force use of getInstance() to create the object
    private DocsAccess(){}
    public static DocsAccess getInstance() {
        return docsAccess;
    }

    //https://docs.oracle.com/javase/8/docs/api/index.html
    public boolean existDoc(String filename) {
        File file = new File("Documents\\",filename); //String parent- String child
        if (file.exists()&&file.isFile()) return true;
        return false;
    }
}