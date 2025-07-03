package com.hospitalmanagement;
import java.sql.*;
import java.util.Scanner;

public class Patient {

    public static void addPatient(Scanner scn, Connection conn) {
        try {
            System.out.print("Enter patient name: ");
            scn.nextLine();
            String name = scn.nextLine();

            System.out.print("Enter age: ");
            int age = scn.nextInt();

            System.out.print("Enter gender (M/F): ");
            String gender = scn.next();

            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, gender);
            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient added successfully.");
            } else {
                System.out.println("Failed to add patient.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewPatients(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");

            System.out.println("\n--- Patients List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age") + ", Gender: " + rs.getString("gender"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
