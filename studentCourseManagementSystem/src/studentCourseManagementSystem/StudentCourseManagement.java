package studentCourseManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class StudentCourseManagement {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Student Course Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. View Enrollments");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addCourse();
                case 3 -> enrollStudent();
                case 4 -> viewEnrollments();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void addStudent() {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            String sql = "INSERT INTO students(name, email) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();

            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void addCourse() {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.print("Enter Course Title: ");
            String title = sc.nextLine();

            String sql = "INSERT INTO courses(title) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.executeUpdate();

            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    private static void enrollStudent() {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.print("Enter Student ID: ");
            int sid = sc.nextInt();
            System.out.print("Enter Course ID: ");
            int cid = sc.nextInt();

            String check = "SELECT * FROM enrollments WHERE student_id=? AND course_id=?";
            PreparedStatement checkStmt = con.prepareStatement(check);
            checkStmt.setInt(1, sid);
            checkStmt.setInt(2, cid);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Student already enrolled in this course.");
                return;
            }

            String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setInt(2, cid);
            ps.executeUpdate();

            System.out.println("Enrollment successful.");
        } catch (SQLException e) {
            System.out.println("Error enrolling student: " + e.getMessage());
        }
    }

    private static void viewEnrollments() {
        try (Connection con = DatabaseConnection.getConnection()) {
            String sql = "SELECT s.name, c.title FROM enrollments e " +
                         "JOIN students s ON e.student_id = s.id " +
                         "JOIN courses c ON e.course_id = c.id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("--- Enrollments ---");
            while (rs.next()) {
                System.out.println("Student: " + rs.getString("name") + ", Course: " + rs.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching enrollments: " + e.getMessage());
        }
    }
}
