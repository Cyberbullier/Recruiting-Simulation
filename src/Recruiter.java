public class Recruiter extends User {

    private Company company;

    public Recruiter(String username, String password){
        super(username, password);
    }

    public Recruiter(String username, String password, Company company) {
        super(username, password);
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
