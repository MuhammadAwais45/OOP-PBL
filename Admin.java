public class Admin extends Person {
    private String password;
    public Admin(String name, String contact, String password) {
        super(name, contact);
        this.password = password;
    }
    public boolean authenticate(String inputPassword) {
        return password.equals(inputPassword);
    }
}
