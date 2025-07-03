package com.hospitalmanagement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Doctor {
    public static void viewDoctors(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");

            System.out.println("\n--- Doctors List ---");
            while (rs.next()) {
                System.out.println("Doctor ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Specialty: " + rs.getString("specialty"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Scanner scn, Connection conn) {
        try {
            System.out.print("Enter patient name: ");
            scn.nextLine();
            String patientName = scn.nextLine();

            System.out.print("Enter doctor ID: ");
            int docId = scn.nextInt();

            System.out.print("Enter appointment date (YYYY-MM-DD): ");
            scn.nextLine();
            String date = scn.nextLine();

            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(
                    "INSERT INTO appointments (patient_name, doctor_id, appointment_date) VALUES ('" +
                            patientName + "', " + docId + ", '" + date + "')");

            if (rows > 0) {
                System.out.println("Appointment booked successfully.");
            } else {
                System.out.println("Failed to book appointment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

