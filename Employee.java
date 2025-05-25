public class Employee extends Person {
    private int employeeId;
    private String role;
    private String email;
    public Employee(int employeeId, String name, String contact, String email, String role) {
        super(name, contact);
        this.employeeId = employeeId;
        this.role = role;
        this.email = email;
    }
    public int getEmployeeId() { return employeeId; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    @Override
    public String toString() {
        return String.format("Employee ID: %d, Name: %s, Contact: %s, Email: %s, Role: %s",
                employeeId, getName(), getContact(), email, role);
    }
}