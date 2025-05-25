import java.io.*;
import java.util.*;

public class RoomManagement {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private int maxRoomNumber = 0;
    private final String fileName = "rooms.csv";

    public RoomManagement() {
        loadRooms();
    }

    public boolean addRoom(int number, Room.RoomType type, double price) {
        if(rooms.containsKey(number)) return false;
        rooms.put(number, new Room(number, type, price, true));
        if(number > maxRoomNumber) maxRoomNumber = number;
        saveRooms();
        return true;
    }

    public boolean updateRoom(int number, Room.RoomType type, double price) {
        Room r = rooms.get(number);
        if(r == null) return false;
        r.setRoomType(type);
        r.setPrice(price);
        saveRooms();
        return true;
    }

    public boolean deleteRoom(int number) {
        boolean deleted = rooms.remove(number) != null;
        if(deleted) saveRooms();
        return deleted;
    }

    public Room getRoom(int number) {
        return rooms.get(number);
    }

    public Collection<Room> getAllRooms() {
        return rooms.values();
    }

    public void updatePriceByCategory(Room.RoomType type, double newPrice) {
        boolean changed = false;
        for(Room r : rooms.values()) {
            if(r.getRoomType() == type) {
                r.setPrice(newPrice);
                changed = true;
            }
        }
        if(changed) saveRooms();
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for(Room r : rooms.values()) {
            if(r.isAvailable()) available.add(r);
        }
        return available;
    }

    private void loadRooms() {
        rooms.clear();
        File file = new File(fileName);
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 4) {
                    int num = Integer.parseInt(parts[0]);
                    Room.RoomType type = Room.RoomType.valueOf(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    boolean available = Boolean.parseBoolean(parts[3]);
                    Room r = new Room(num, type, price, available);
                    rooms.put(num, r);
                    if(num > maxRoomNumber) maxRoomNumber = num;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading rooms: "+e.getMessage());
        }
    }

    public void saveRooms() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for(Room r : rooms.values()) {
                pw.printf("%d,%s,%.2f,%b%n", r.getRoomNumber(), r.getRoomType().name(), r.getPrice(), r.isAvailable());
            }
        } catch (Exception e) {
            System.out.println("Error saving rooms: "+e.getMessage());
        }
    }
}
