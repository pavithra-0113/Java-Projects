package com.hospitalmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class HospitalManagement {

    static final String DB_URL = "jdbc:mysql://localhost:3306/hospitaldb";
    static final String USER = "root";
    static final String PASS = "pavisql123"; // Replace with your actual MySQL password

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        if (!login(scn)) {
            System.out.println("Too many failed attempts. Exiting...");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            int choice;
            do {
                System.out.println("\n--- Hospital Management Menu ---");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scn.nextInt();

                switch (choice) {
                    case 1:
                        Patient.addPatient(scn, conn);
                        break;
                    case 2:
                        Patient.viewPatients(conn);
                        break;
                    case 3:
                        Doctor.viewDoctors(conn);
                        break;
                    case 4:
                        Doctor.bookAppointment(scn, conn);
                        break;
                    case 5:
                        System.out.println("Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scn.close();
        }
    }

    public static boolean login(Scanner scn) {
        final String USERNAME = "admin";
        final String PASSWORD = "admin123";
        int attempts = 3;

        while (attempts-- > 0) {
            System.out.print("Enter username: ");
            String user = scn.next();
            System.out.print("Enter password: ");
            String pass = scn.next();

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                System.out.println("Login successful!\n");
                return true;
            } else {
                System.out.println("Invalid credentials. Attempts left: " + attempts);
            }
        }
        return false;
    }
}

