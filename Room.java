public class Room {
    public enum RoomType { 
        SINGLE(0), DOUBLE(0), SUITE(0);
        
        private double price;
        RoomType(double price) {
            this.price = price;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
    }

    private int roomNumber;
    private RoomType roomType;
    private boolean isAvailable;

    public Room(int roomNumber, RoomType roomType, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public double getPrice() { return roomType.getPrice(); }
    public boolean isAvailable() { return isAvailable; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return String.format("Room %d (%s), Price: $%.2f, Available: %s", roomNumber, roomType, roomType.getPrice(), isAvailable ? "Yes" : "No");
    }
}
