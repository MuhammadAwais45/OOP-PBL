import java.text.SimpleDateFormat;

public class Billing {
    public static void printBill(Stay stay) {
        System.out.println("---------- HOTEL BILL ----------");
        System.out.println("Stay ID: " + stay.getStayId());
        System.out.println("Customer: " + stay.getCustomer().getName());
        System.out.println("Room: " + stay.getRoom().getRoomNumber() + " (" + stay.getRoom().getRoomType() + ")");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Check-in: " + sdf.format(stay.getCheckinDate()));
        System.out.println("Check-out: " + sdf.format(stay.getCheckoutDate()));
        System.out.printf("Total Amount (Cash): $%.2f%n", stay.getTotalPrice());
        System.out.println("------------------------------");
    }
}
