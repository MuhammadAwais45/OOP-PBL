public class Customer extends Person {
    private int customerId;
    private String cnic;
    public Customer(int customerId, String name, String contact, String cnic) {
        super(name, contact);
        this.customerId = customerId;
        this.cnic = cnic;
    }
    public int getCustomerId() { return customerId; }
    public String getCnic() { return cnic; }
    public void setCnic(String cnic) { this.cnic = cnic; }
    @Override
    public String toString() {
        return String.format("Customer ID: %d, Name: %s, Contact: %s, CNIC: %s", customerId, getName(), getContact(), cnic);
    }
}