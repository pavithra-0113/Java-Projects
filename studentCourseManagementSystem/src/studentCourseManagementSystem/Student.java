package studentCourseManagementSystem;

public class Student {
	
	private int id;
    private String name;
    private String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Student{" + "ID=" + id + ", Name='" + name + "', Email='" + email + "'}";
    }

}
