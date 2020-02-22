import java.io.Serializable;
public class Company implements Serializable
{
    private String name;
    private Company owner;
    // TODO: need to implement list of employees

    public Company(String name) {
        this.name = name;
        owner=null;
    }

    public Company(String name, Company owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company owner) {
        this.owner = owner;
    }
}
