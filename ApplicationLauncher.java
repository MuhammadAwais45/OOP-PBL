import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ApplicationLauncher {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Admin admin = new Admin("Admin", "0000000000", "admin123");
    private static final RoomManagement roomMgmt = new RoomManagement();
    private static final CustomerManagement customerMgmt = new CustomerManagement();
    private static final EmployeeManagement employeeMgmt = new EmployeeManagement();
    private static final StayManagement stayMgmt = new StayManagement(roomMgmt, customerMgmt);

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Management System Console");

        if (!login()) {
            System.out.println("Authentication failed. Exiting...");
            return;
        }

        int choice;
        do {
            printMainMenu();
            choice = readInt();
            switch (choice) {
                case 1 -> roomsMenu();
                case 2 -> customersMenu();
                case 3 -> employeesMenu();
                case 4 -> staysMenu();
                case 5 -> generateBillMenu();
                case 6 -> viewRevenueReport();
                case 7 -> System.out.println("Exiting. Thank you for using the system.");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);
    }

    private static boolean login() {
        System.out.print("Enter admin password: ");
        String input = scanner.nextLine();
        if (admin.authenticate(input)) {
            System.out.println("Login successful!");
            return true;
        }
        System.out.println("Invalid password.");
        return false;
    }

    private static void printMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Manage Rooms");
        System.out.println("2. Manage Customers");
        System.out.println("3. Manage Employees");
        System.out.println("4. Manage Stays");
        System.out.println("5. Generate Bill");
        System.out.println("6. View Revenue Report");
        System.out.println("7. Exit");
        System.out.print("Enter option: ");
    }

    private static void roomsMenu() {
        int ch;
        do {
            System.out.println("\nROOM MANAGEMENT");
            System.out.println("1. Add Room");
            System.out.println("2. Update Room");
            System.out.println("3. Delete Room");
            System.out.println("4. View All Rooms");
            System.out.println("5. Update Price By Category");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");
            ch = readInt();
            switch (ch) {
                case 1 -> addRoom();
                case 2 -> updateRoom();
                case 3 -> deleteRoom();
                case 4 -> viewAllRooms();
                case 5 -> updatePriceByCategory();
                case 6 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (ch != 6);
    }

    private static void addRoom() {
        System.out.print("Enter room number: ");
        int num = readInt();
        Room.RoomType type = readRoomType();
        boolean added = roomMgmt.addRoom(num, type);
        if (added) System.out.println("Room added.");
        else System.out.println("Room number already exists.");
    }

    private static void updateRoom() {
        System.out.print("Enter room number to update: ");
        int num = readInt();
        Room room = roomMgmt.getRoom(num);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        Room.RoomType type = readRoomType();
        roomMgmt.updateRoom(num, type);
        System.out.println("Room updated.");
    }

    private static void deleteRoom() {
        System.out.print("Enter room number to delete: ");
        int num = readInt();
        boolean deleted = roomMgmt.deleteRoom(num);
        if (deleted) System.out.println("Room deleted.");
        else System.out.println("Room not found.");
    }

    private static void viewAllRooms() {
        List<Room> rooms = roomMgmt.getAvailableRooms();
        if (rooms.isEmpty()) System.out.println("No rooms found.");
        else rooms.forEach(System.out::println);
    }

    private static void updatePriceByCategory() {
        Room.RoomType type = readRoomType();
        System.out.print("Enter new price for all " + type + " rooms: ");
        double price = readDouble();
        roomMgmt.updatePriceByCategory(type, price);
        System.out.println("Prices updated.");
    }

    private static void customersMenu() {
        int ch;
        do {
            System.out.println("\nCUSTOMER MANAGEMENT");
            System.out.println("1. Add Customer");
            System.out.println("2. Search Customer By Name");
            System.out.println("3. View All Customers");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            ch = readInt();
            switch (ch) {
                case 1 -> addCustomer();
                case 2 -> searchCustomer();
                case 3 -> viewAllCustomers();
                case 4 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (ch != 4);
    }

    private static void addCustomer() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter customer contact: ");
        String contact = scanner.nextLine().trim();
        System.out.print("Enter 13-digit CNIC: ");
        String cnic = scanner.nextLine().trim();
        if (!cnic.matches("\\d{13}")) {
            System.out.println("Invalid CNIC format.");
            return;
        }
        Customer c = customerMgmt.addCustomer(name, contact, cnic);
        if (c == null) System.out.println("Customer CNIC already exists.");
        else System.out.println("Customer added with ID: " + c.getCustomerId());
    }

    private static void searchCustomer() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        List<Customer> results = customerMgmt.searchCustomersByName(name);
        if (results.isEmpty()) System.out.println("No customers found.");
        else {
            System.out.println("Search results:");
            results.forEach(System.out::println);
        }
    }

    private static void viewAllCustomers() {
        List<Customer> customers = new ArrayList<>(customerMgmt.getAllCustomers());
        if (customers.isEmpty()) System.out.println("No customers found.");
        else customers.forEach(System.out::println);
    }

    private static void employeesMenu() {
        int ch;
        do {
            System.out.println("\nEMPLOYEE MANAGEMENT");
            System.out.println("1. Add Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Update Employee Information");
            System.out.println("4. View All Employees");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            ch = readInt();
            switch (ch) {
                case 1 -> addEmployee();
                case 2 -> removeEmployee();
                case 3 -> updateEmployee();
                case 4 -> viewAllEmployees();
                case 5 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (ch != 5);
    }

    private static void addEmployee() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter employee contact: ");
        String contact = scanner.nextLine().trim();
        System.out.print("Enter employee email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter employee role: ");
        String role = scanner.nextLine().trim();

        Employee e = employeeMgmt.addEmployee(name, contact, email, role);
        System.out.println("Employee added with ID: " + e.getEmployeeId());
    }

    private static void removeEmployee() {
        System.out.print("Enter employee ID to remove: ");
        int id = readInt();
        boolean removed = employeeMgmt.removeEmployee(id);
        if (removed) System.out.println("Employee removed.");
        else System.out.println("Employee not found.");
    }

    private static void updateEmployee() {
        System.out.print("Enter employee ID to update: ");
        int id = readInt();
        Employee e = employeeMgmt.getEmployee(id);
        if (e == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.println("Leave input blank to keep current value.");

        System.out.print("Enter new name (current: " + e.getName() + "): ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter new contact (current: " + e.getContact() + "): ");
        String contact = scanner.nextLine().trim();
        System.out.print("Enter new email (current: " + e.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter new role (current: " + e.getRole() + "): ");
        String role = scanner.nextLine().trim();

        boolean updated = employeeMgmt.updateEmployee(id,
                name.isEmpty() ? null : name,
                contact.isEmpty() ? null : contact,
                email.isEmpty() ? null : email,
                role.isEmpty() ? null : role);
        if (updated) System.out.println("Employee updated.");
        else System.out.println("Update failed.");
    }

    private static void viewAllEmployees() {
        List<Employee> employees = new ArrayList<>(employeeMgmt.getAllEmployees());
        if (employees.isEmpty()) System.out.println("No employees found.");
        else {
            System.out.println("All Employees:");
            employees.forEach(System.out::println);
        }
    }

    private static void staysMenu() {
        int ch;
        do {
            System.out.println("\nSTAY MANAGEMENT");
            System.out.println("1. Check-in Customer");
            System.out.println("2. Check-out Stay");
            System.out.println("3. View All Stays");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter choice: ");
            ch = readInt();
            switch (ch) {
                case 1 -> checkIn();
                case 2 -> checkOut();
                case 3 -> viewAllStays();
                case 4 -> System.out.println("Returning to Main Menu...");
                default -> System.out.println("Invalid choice.");
            }
        } while (ch != 4);
    }

    private static void checkIn() {
        System.out.print("Enter Customer ID: ");
        int custId = readInt();
        Customer customer = customerMgmt.getCustomer(custId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.print("Enter Room Number: ");
        int roomNum = readInt();
        Room room = roomMgmt.getRoom(roomNum);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        if (!room.isAvailable()) {
            System.out.println("Room currently not available.");
            return;
        }
        System.out.print("Enter Check-in date (dd/MM/yyyy): ");
        Date checkin = readDate();
        if (checkin == null) return;
        System.out.print("Enter Check-out date (dd/MM/yyyy): ");
        Date checkout = readDate();
        if (checkout == null) return;

        if (checkout.before(checkin)) {
            System.out.println("Check-out date must be after check-in date.");
            return;
        }

        Stay stay = stayMgmt.checkIn(customer, room, checkin, checkout);
        if (stay != null) System.out.println("Check-in successful! Stay ID: " + stay.getStayId());
        else System.out.println("Check-in failed.");
    }

    private static void checkOut() {
        System.out.print("Enter Stay ID to check out: ");
        int stayId = readInt();
        Stay stay = stayMgmt.getStayById(stayId);
        if (stay == null) {
            System.out.println("Stay not found.");
            return;
        }
        if (stay.getStatus() == StayStatus.CHECKED_OUT) {
            System.out.println("Stay already checked-out.");
            return;
        }
        boolean success = stayMgmt.checkOut(stayId);
        if (success) {
            System.out.println("Check-out successful.");
            System.out.println("Generating bill:");
            Billing.printBill(stay);
        } else System.out.println("Check-out failed.");
    }

    private static void viewAllStays() {
        List<Stay> stays = new ArrayList<>(stayMgmt.getAllStays());
        if (stays.isEmpty()) System.out.println("No stays recorded.");
        else stays.forEach(System.out::println);
    }

    private static void generateBillMenu() {
        System.out.print("Enter Stay ID to generate bill: ");
        int stayId = readInt();
        Stay stay = stayMgmt.getStayById(stayId);
        if (stay == null) System.out.println("Stay not found.");
        else Billing.printBill(stay);
    }

    private static void viewRevenueReport() {
        double totalRevenue = stayMgmt.getTotalRevenue();
        System.out.printf("Total Revenue Collected: $%.2f%n", totalRevenue);
    }

    // Utility input methods
    public static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Double.parseDouble(line.trim());
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a decimal number: ");
            }
        }
    }

    public static Date readDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        while (true) {
            try {
                String line = scanner.nextLine();
                return sdf.parse(line.trim());
            } catch (Exception e) {
                System.out.print("Invalid date format. Please enter date as dd/MM/yyyy: ");
            }
        }
    }

    public static Room.RoomType readRoomType() {
        System.out.println("Choose Room Type:");
        for (Room.RoomType t : Room.RoomType.values()) {
            System.out.println("- " + t);
        }
        System.out.print("Enter type: ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Room.RoomType.valueOf(input);
            } catch (Exception e) {
                System.out.print("Invalid type. Enter again (SINGLE, DOUBLE, SUITE): ");
            }
        }
    }
}
