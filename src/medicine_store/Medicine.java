package medicine_store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Medicine {
    private String name;
    private double price;
    private int stock;

    public Medicine(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/medicine_store";
    private static final String USER = "root";
    private static final String PASS = "Nikhi$123";

    public static void main(String[] args) {
        // Open a connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to the database.");

            // Create a list to store medicines
            List<Medicine> medicines = new ArrayList<>();

            // Prompt the user to enter five types of medicines
            @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < 1; i++) {
                System.out.println("Enter details for Medicine " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Price: ");
                double price = scanner.nextDouble();
                System.out.print("Stock: ");
                int stock = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                Medicine medicine = new Medicine(name, price, stock);
                medicines.add(medicine);
            }

            // Insert each medicine into the database
            for (Medicine medicine : medicines) {
                insertMedicine(connection, medicine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to insert a new Medicine record into the database
    private static void insertMedicine(Connection connection, Medicine medicine) throws SQLException {
        String query = "INSERT INTO medicine (name, price, stock) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setDouble(2, medicine.getPrice());
            preparedStatement.setInt(3, medicine.getStock());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Medicine record inserted successfully: " + medicine);
            } else {
                System.out.println("Failed to insert the medicine record: " + medicine);
            }
        }
    }
   
    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock1(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " (Price: $" + price + ", Stock: " + stock + ")";
    }

	public void setStock(int newStock) {
		// TODO Auto-generated method stub
		
	}
}
