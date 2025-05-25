import java.util.Date;
import java.text.SimpleDateFormat;

public class Stay {
    private int stayId;
    private Customer customer;
    private Room room;
    private Date checkinDate;
    private Date checkoutDate;
    private StayStatus status;

    public Stay(int stayId, Customer customer, Room room, Date checkinDate, Date checkoutDate, StayStatus status) {
        this.stayId = stayId;
        this.customer = customer;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.status = status;
    }

    public int getStayId() { return stayId; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public Date getCheckinDate() { return checkinDate; }
    public Date getCheckoutDate() { return checkoutDate; }
    public StayStatus getStatus() { return status; }

    public void setStatus(StayStatus status) { this.status = status; }
    public void setCheckinDate(Date checkinDate) { this.checkinDate = checkinDate; }
    public void setCheckoutDate(Date checkoutDate) { this.checkoutDate = checkoutDate; }

    public double getTotalPrice() {
        long diff = checkoutDate.getTime() - checkinDate.getTime();
        long days = diff / (1000L * 60 * 60 * 24);
        if(days == 0) days = 1;
        return days * room.getPrice();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Stay ID: %d, Customer: %s, Room: %d, Check-in: %s, Check-out: %s, Status: %s, Total Price: $%.2f",
                stayId, customer.getName(), room.getRoomNumber(), sdf.format(checkinDate), sdf.format(checkoutDate), status, getTotalPrice());
    }
}
