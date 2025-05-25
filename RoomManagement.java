import java.io.*;
import java.util.*;

public class RoomManagement {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private int maxRoomNumber = 0;
    private final String roomFileName = "rooms.csv";
    private final String priceFileName = "room_prices.csv";

    public RoomManagement() {
        loadRoomTypePrices();
        loadRooms();
    }

    
    public boolean addRoom(int number, Room.RoomType type) {
        if(rooms.containsKey(number)) return false;
        rooms.put(number, new Room(number, type, true));
        if(number > maxRoomNumber) maxRoomNumber = number;
        saveRooms();
        return true;
    }

    public boolean updateRoom(int number, Room.RoomType type) {
        Room r = rooms.get(number);
        if(r == null) return false;
        r.setRoomType(type);
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
        type.setPrice(newPrice);
        saveRooms();
        saveRoomTypePrices();
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
        File file = new File(roomFileName);
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length >= 3) {
                    int num = Integer.parseInt(parts[0]);
                    Room.RoomType type = Room.RoomType.valueOf(parts[1]);
                    boolean available = Boolean.parseBoolean(parts[2]);
                    Room r = new Room(num, type, available);
                    rooms.put(num, r);
                    if(num > maxRoomNumber) maxRoomNumber = num;
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading rooms: "+e.getMessage());
        }
    }

    public void saveRooms() {
        try(PrintWriter pw = new PrintWriter(new FileWriter(roomFileName))) {
            for(Room r : rooms.values()) {
                pw.printf("%d,%s,%b%n", r.getRoomNumber(), r.getRoomType().name(), r.isAvailable());
            }
        } catch (Exception e) {
            System.out.println("Error saving rooms: "+e.getMessage());
        }
    }
    public void saveRoomTypePrices() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(priceFileName))) {
            for (Room.RoomType type : Room.RoomType.values()) {
                pw.printf("%s,%.2f%n", type.name(), type.getPrice());
            }
        } catch (IOException e) {
            System.out.println("Error saving room type prices: " + e.getMessage());
        }
    }

    public void loadRoomTypePrices() {
        File file = new File(priceFileName);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Room.RoomType type = Room.RoomType.valueOf(parts[0]);
                    double price = Double.parseDouble(parts[1]);
                    type.setPrice(price);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading room type prices: " + e.getMessage());
        }
    }
}
