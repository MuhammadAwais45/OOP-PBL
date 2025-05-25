import java.io.*;
import java.util.*;

public class EmployeeManagement {
    private final Map<Integer, Employee> employees = new HashMap<>();
    private int maxEmployeeId = 0;
    private final String fileName = "employees.csv";

    public EmployeeManagement() {
        loadEmployees();
    }

    public Employee addEmployee(String name, String contact, String email, String role) {
        int newId = ++maxEmployeeId;
        Employee e = new Employee(newId, name, contact, email, role);
        employees.put(newId, e);
        saveEmployees();
        return e;
    }

    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    public boolean removeEmployee(int id) {
        boolean removed = employees.remove(id) != null;
        if(removed) saveEmployees();
        return removed;
    }

    public boolean updateEmployee(int id, String name, String contact, String email, String role) {
        Employee e = employees.get(id);
        if(e == null) return false;
        if(name != null && !name.isEmpty()) e.setName(name);
        if(contact != null && !contact.isEmpty()) e.setContact(contact);
        if(email != null && !email.isEmpty()) e.setEmail(email);
        if(role != null && !role.isEmpty()) e.setRole(role);
        saveEmployees();
        return true;
    }

    public Collection<Employee> getAllEmployees() {
        return employees.values();
    }

    private void loadEmployees() {
        employees.clear();
        File file = new File(fileName);
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String contact = parts[2];
                    String email = parts[3];
                    String role = parts[4];
                    Employee e = new Employee(id, name, contact, email, role);
                    employees.put(id, e);
                    if(id > maxEmployeeId) maxEmployeeId = id;
                }
            }
        } catch(Exception e) {
            System.out.println("Error loading employees: "+e.getMessage());
        }
    }

    public void saveEmployees() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for(Employee e : employees.values()) {
                pw.printf("%d,%s,%s,%s,%s%n", e.getEmployeeId(), e.getName(), e.getContact(), e.getEmail(), e.getRole());
            }
        } catch (Exception e) {
            System.out.println("Error saving employees: "+e.getMessage());
        }
    }
}

