import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class StayManagement {
    private final Map<Integer, Stay> stays = new HashMap<>();
    private int maxStayId = 0;
    private final String fileName = "stays.csv";

    private final RoomManagement roomMgmt;
    private final CustomerManagement custMgmt;

    public StayManagement(RoomManagement roomMgmt, CustomerManagement custMgmt) {
        this.roomMgmt = roomMgmt;
        this.custMgmt = custMgmt;
        loadStays();
    }

    public boolean isRoomAvailable(Room room) {
        for(Stay stay : stays.values()) {
            if(stay.getRoom().getRoomNumber() == room.getRoomNumber() && stay.getStatus() == StayStatus.CHECKED_IN)
                return false;
        }
        return true;
    }

    public Stay checkIn(Customer customer, Room room, Date checkinDate, Date checkoutDate) {
        if(!isRoomAvailable(room)) return null;
        int newId = ++maxStayId;
        Stay stay = new Stay(newId, customer, room, checkinDate, checkoutDate, StayStatus.CHECKED_IN);
        stays.put(newId, stay);
        room.setAvailable(false);
        saveStays();
        roomMgmt.saveRooms();
        return stay;
    }

    public boolean checkOut(int stayId) {
        Stay stay = stays.get(stayId);
        if(stay != null && stay.getStatus() == StayStatus.CHECKED_IN){
            stay.setStatus(StayStatus.CHECKED_OUT);
            stay.getRoom().setAvailable(true);
            saveStays();
            roomMgmt.saveRooms();
            return true;
        }
        return false;
    }

    public Stay getStayById(int stayId) {
        return stays.get(stayId);
    }

    public Collection<Stay> getAllStays() {
        return stays.values();
    }

    public double getTotalRevenue() {
        double total = 0.0;
        for(Stay s : stays.values()) {
            if(s.getStatus() == StayStatus.CHECKED_OUT) total += s.getTotalPrice();
        }
        return total;
    }

    private void loadStays() {
        stays.clear();
        File file = new File(fileName);
        if(!file.exists()) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 6) {
                    int stayId = Integer.parseInt(parts[0]);
                    int custId = Integer.parseInt(parts[1]);
                    int roomNum = Integer.parseInt(parts[2]);
                    Date checkinDate = sdf.parse(parts[3]);
                    Date checkoutDate = sdf.parse(parts[4]);
                    StayStatus status = StayStatus.valueOf(parts[5]);
                    Customer customer = custMgmt.getCustomer(custId);
                    Room room = roomMgmt.getRoom(roomNum);
                    if(customer != null && room != null) {
                        Stay stay = new Stay(stayId, customer, room, checkinDate, checkoutDate, status);
                        stays.put(stayId, stay);
                        if(stayId > maxStayId) maxStayId = stayId;
                        room.setAvailable(status == StayStatus.CHECKED_OUT);
                    }
                }
            }
            roomMgmt.saveRooms(); // sync availability
        } catch(Exception e) {
            System.out.println("Error loading stays: "+e.getMessage());
        }
    }

    public void saveStays() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for(Stay s : stays.values()) {
                pw.printf("%d,%d,%d,%s,%s,%s%n",
                        s.getStayId(), s.getCustomer().getCustomerId(), s.getRoom().getRoomNumber(),
                        sdf.format(s.getCheckinDate()), sdf.format(s.getCheckoutDate()), s.getStatus().name());
            }
        } catch(Exception e) {
            System.out.println("Error saving stays: "+e.getMessage());
        }
    }
}
