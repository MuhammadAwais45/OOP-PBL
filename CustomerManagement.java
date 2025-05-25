import java.io.*;
import java.util.*;

public class CustomerManagement {
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final Map<String, Customer> cnicMap = new HashMap<>();
    private int maxCustomerId = 0;
    private final String fileName = "customers.csv";

    public CustomerManagement() {
        loadCustomers();
    }

    public Customer addCustomer(String name, String contact, String cnic) {
        if(cnicMap.containsKey(cnic)) return null;
        int newId = ++maxCustomerId;
        Customer c = new Customer(newId, name, contact, cnic);
        customers.put(newId, c);
        cnicMap.put(cnic, c);
        saveCustomers();
        return c;
    }

    public Customer getCustomer(int id) {
        return customers.get(id);
    }

    public Customer getCustomerByCnic(String cnic) {
        return cnicMap.get(cnic);
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> res = new ArrayList<>();
        for(Customer c : customers.values()) {
            if(c.getName().toLowerCase().contains(name.toLowerCase())) {
                res.add(c);
            }
        }
        return res;
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    private void loadCustomers() {
        customers.clear();
        cnicMap.clear();
        File file = new File(fileName);
        if(!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String contact = parts[2];
                    String cnic = parts[3];
                    Customer c = new Customer(id, name, contact, cnic);
                    customers.put(id, c);
                    cnicMap.put(cnic, c);
                    if(id > maxCustomerId) maxCustomerId = id;
                }
            }
        } catch(Exception e) {
            System.out.println("Error loading customers: "+e.getMessage());
        }
    }

    public void saveCustomers() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for(Customer c : customers.values()) {
                pw.printf("%d,%s,%s,%s%n", c.getCustomerId(), c.getName(), c.getContact(), c.getCnic());
            }
        } catch (Exception e) {
            System.out.println("Error saving customers: "+e.getMessage());
        }
    }
}
