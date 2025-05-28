# Hotel Management System

## 🏨 Overview

This is a **Java console-based Hotel Management System** that allows an admin to manage:

✅ Rooms  
✅ Customers  
✅ Employees  
✅ Stays (Check-in & Check-out)  
✅ Billing & Revenue Reports

It provides a **menu-driven interface** with options to add, update, delete, and view records, generate bills, and track total hotel revenue.

---

## 💻 How to Run

1. **Compile the Java files:**

   Navigate to the `src` directory and run:
   ```bash
   javac *.java
   ```

2. **Run the main launcher:**
   ```bash
   java ApplicationLauncher
   ```

3. **Admin Login:**
   - Default password:
     ```
     admin123
     ```

---

## 📂 Project Structure

```
HotelManagementSystem/
├── ApplicationLauncher.java
├── Admin.java
├── Room.java
├── RoomManagement.java
├── Customer.java
├── CustomerManagement.java
├── Employee.java
├── EmployeeManagement.java
├── Stay.java
├── StayManagement.java
├── Billing.java
└── README.md
```

---

## 🛠️ Features

- **Room Management**: Add, update, delete, and view rooms; update prices by category.
- **Customer Management**: Add new customers, search by name, view all customers.
- **Employee Management**: Add, update, remove employees; view all employee records.
- **Stay Management**: Check-in customers, check-out stays, view stay records.
- **Billing**: Generate detailed bills upon check-out.
- **Revenue Reporting**: View total collected revenue.

---

## 🔑 Admin Details

| Username | Password    |
|----------|-------------|
| Admin    | admin123    |

---

## ✨ Technologies Used

- Java (JDK 17+ recommended)
- Console-based I/O
- OOP (Object-Oriented Programming)

---

## 📜 Notes

- Ensure you run the program in a console that supports standard input and output.
- The system is for demonstration purposes — it uses in-memory storage and will reset data upon each restart.
