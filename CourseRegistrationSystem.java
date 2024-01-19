import java.util.Scanner;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
}

class Student {
    String studentID;
    String name;
    String[] registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new String[5]; // Assuming a student can register for up to 5 courses
    }
}

public class CourseRegistrationSystem {
    static Course[] courses;
    static Student[] students;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayCourseListing();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    removeRegistration();
                    break;
                case 4:
                    displayRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting the system. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeData() {
        // Initialize course database
        courses = new Course[] {
                new Course("CSE101", "Introduction to Programming", "Basic programming concepts", 30, "MWF 10:00 AM"),
                new Course("MAT201", "Calculus I", "Single-variable calculus", 25, "TTH 1:30 PM"),
                // Add more courses as needed
        };

        // Initialize student database
        students = new Student[] {
                new Student("S001", "John Doe"),
                new Student("S002", "Jane Smith"),
                // Add more students as needed
        };
    }

    private static void displayMenu() {
        System.out.println("\nStudent Course Registration System");
        System.out.println("1. Display Course Listing");
        System.out.println("2. Register for Courses");
        System.out.println("3. Remove Course Registration");
        System.out.println("4. Display Registered Courses");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void displayCourseListing() {
        System.out.println("\nCourse Listing:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.code);
            System.out.println("Title: " + course.title);
            System.out.println("Description: " + course.description);
            System.out.println("Capacity: " + course.capacity);
            System.out.println("Schedule: " + course.schedule);
            System.out.println("Available Slots: " + (course.capacity - countRegistrations(course.code)));
            System.out.println();
        }
    }

    private static void registerStudent() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);

        if (student != null) {
            displayCourseListing();
            System.out.print("Enter Course Code to Register: ");
            String courseCode = scanner.next();

            Course course = findCourse(courseCode);
            if (course != null && countRegistrations(courseCode) < course.capacity) {
                student.registeredCourses[findEmptySlot(student.registeredCourses)] = courseCode;
                System.out.println("Registration successful for " + course.title);
            } else {
                System.out.println("Invalid course code or no available slots.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void removeRegistration() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);

        if (student != null) {
            displayRegisteredCourses(student);
            System.out.print("Enter Course Code to Remove: ");
            String courseCode = scanner.next();

            if (containsCourse(student.registeredCourses, courseCode)) {
                removeCourse(student.registeredCourses, courseCode);
                System.out.println("Course removal successful.");
            } else {
                System.out.println("Student is not registered for the given course.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayRegisteredCourses() {
        System.out.print("\nEnter Student ID: ");
        String studentID = scanner.next();
        Student student = findStudent(studentID);

        if (student != null) {
            displayRegisteredCourses(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayRegisteredCourses(Student student) {
        System.out.println("\nRegistered Courses for " + student.name + ":");
        for (String courseCode : student.registeredCourses) {
            if (courseCode != null) {
                Course course = findCourse(courseCode);
                if (course != null) {
                    System.out.println(course.title + " (" + courseCode + ")");
                }
            }
        }
    }

    private static int countRegistrations(String courseCode) {
        int count = 0;
        for (Student student : students) {
            if (containsCourse(student.registeredCourses, courseCode)) {
                count++;
            }
        }
        return count;
    }

    private static boolean containsCourse(String[] courses, String courseCode) {
        for (String code : courses) {
            if (code != null && code.equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    private static void removeCourse(String[] courses, String courseCode) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null && courses[i].equals(courseCode)) {
                courses[i] = null;
                return;
            }
        }
    }

    private static int findEmptySlot(String[] courses) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.code.equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudent(String studentID) {
        for (Student student : students) {
            if (student.studentID.equals(studentID)) {
                return student;
            }
        }
        return null;
    }
}
