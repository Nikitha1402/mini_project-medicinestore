package medicine_store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Billing {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/medicine_store";
    private static final String USER = "root";
    private static final String PASS = "Nikhi$123";
    private static Connection connection;
    private ArrayList<Medicine> cart;


    public Billing() {
        cart = new ArrayList<>(); // Initialize the cart ArrayList
    }

    public void addToCart(Medicine medicine, int quantity) {
        // Check if the requested quantity is available in the inventory
        if (medicine.getStock() >= quantity) {
            cart.add(new Medicine(medicine.getName(), medicine.getPrice(), quantity));
            // Update the stock in the inventory
            medicine.setStock(medicine.getStock() - quantity);
        } else {
            System.out.println("Insufficient stock for " + medicine.getName());
        }
    }


    public double calculateTotalAmount() {
        double totalAmount = 0;
        for (Medicine medicine : cart) {
            totalAmount += medicine.getPrice() * medicine.getStock();
        }
        return totalAmount;
    }

    public void generateBill() {
        System.out.println("--------- Bill Receipt ---------");
        for (Medicine medicine : cart) {
            System.out.println("Medicine: " + medicine.getName());
            System.out.println("Price per unit: $" + medicine.getPrice());
            System.out.println("Quantity: " + medicine.getStock());
            System.out.println("--------------------------------");
        }
        System.out.println("Total Amount: $" + calculateTotalAmount());
        System.out.println("------------------------------");
    }
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to add a new entry to the billing table
    public static void addToBillingTable(Medicine medicine, int quantity) {
        String sql = "INSERT INTO billing (medicine_name, price_per_unit, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, medicine.getName());
            pstmt.setDouble(2, medicine.getPrice());
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all entries from the billing table
    public static List<Medicine> getAllBillingEntries() {
        List<Medicine> billingEntries = new ArrayList<>();
        String sql = "SELECT medicine_name, price_per_unit, quantity FROM billing";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("medicine_name");
                double price = rs.getDouble("price_per_unit");
                int quantity = rs.getInt("quantity");
                billingEntries.add(new Medicine(name, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billingEntries;
    }
}
