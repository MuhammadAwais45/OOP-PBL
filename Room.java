public class Room {
    public enum RoomType { SINGLE, DOUBLE, SUITE }

    private int roomNumber;
    private RoomType roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType roomType, double price, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return String.format("Room %d (%s), Price: $%.2f, Available: %s", roomNumber, roomType, price, isAvailable ? "Yes" : "No");
    }
}
