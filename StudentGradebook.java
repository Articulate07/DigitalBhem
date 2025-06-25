import java.util.*;

public class StudentGradebook {

    // ===== Student Class =====
    static class Student {
        private String name;
        private String studentID;
        private Map<String, Integer> subjects;

        public Student(String name, String studentID) {
            this.name = name;
            this.studentID = studentID;
            this.subjects = new HashMap<>();
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStudentID() {
            return studentID;
        }

        public String getName() {
            return name;
        }

        public void addSubject(String subjectName, int marks) {
            subjects.put(subjectName, marks);
        }

        public void clearSubjects() {
            subjects.clear();
        }

        public double calculateAverage() {
            if (subjects.isEmpty()) return 0;
            int total = 0;
            for (int marks : subjects.values()) {
                total += marks;
            }
            return total / (double) subjects.size();
        }

        public String getLetterGrade(double average) {
            if (average >= 90) return "A";
            else if (average >= 80) return "B";
            else if (average >= 70) return "C";
            else if (average >= 60) return "D";
            else return "F";
        }

        public void displayInfo() {
            System.out.println("👤 Name: " + name + " | ID: " + studentID);
            System.out.println("📘 Subjects & Marks:");
            for (Map.Entry<String, Integer> entry : subjects.entrySet()) {
                System.out.println("   - " + entry.getKey() + ": " + entry.getValue());
            }
            double average = calculateAverage();
            System.out.printf("📊 Average Score: %.2f\n", average);
            System.out.println("🎓 Grade: " + getLetterGrade(average));
        }
    }

    // ===== Main Menu System =====
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n🎓 Student Gradebook Menu");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Remove Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.println("6. Generate Report Summary");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    editStudent();
                    break;
                case 3:
                    removeStudent();
                    break;
                case 4:
                    displayAll();
                    break;
                case 5:
                    System.out.println("👋 Exiting Gradebook. Goodbye!");
                    break;
                case 6:
                    generateSummary();
                    break;
                default:
                    System.out.println("❌ Invalid option! Try again.");
            }
        } while (choice != 5);
    }

    private static void addStudent() {
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = new Student(name, id);

        System.out.print("Number of subjects: ");
        int subCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < subCount; i++) {
            System.out.print("Subject " + (i + 1) + " Name: ");
            String sub = scanner.nextLine();
            System.out.print("Marks: ");
            int marks = scanner.nextInt();
            scanner.nextLine();
            student.addSubject(sub, marks);
        }

        students.add(student);
        System.out.println("✅ Student added successfully!");
    }

    private static void editStudent() {
        System.out.print("Enter Student ID to edit: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("❌ Student not found.");
            return;
        }

        System.out.print("Enter new name (or press Enter to keep unchanged): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Do you want to update subjects? (Y/N): ");
        String updateSub = scanner.nextLine();
        if (updateSub.equalsIgnoreCase("Y")) {
            student.clearSubjects();

            System.out.print("Number of new subjects: ");
            int subCount = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < subCount; i++) {
                System.out.print("Subject " + (i + 1) + " Name: ");
                String sub = scanner.nextLine();
                System.out.print("Marks: ");
                int marks = scanner.nextInt();
                scanner.nextLine();
                student.addSubject(sub, marks);
            }
        }

        System.out.println("✅ Student updated.");
    }

    private static void removeStudent() {
        System.out.print("Enter Student ID to remove: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("❌ Student not found.");
            return;
        }
        students.remove(student);
        System.out.println("✅ Student removed.");
    }

    private static void displayAll() {
        if (students.isEmpty()) {
            System.out.println("📭 No student records.");
            return;
        }

        System.out.println("\n📋 Student Gradebook:");
        for (Student s : students) {
            s.displayInfo();
            System.out.println("----------------------------");
        }
    }

    private static void generateSummary() {
        if (students.isEmpty()) {
            System.out.println("📭 No data available to generate reports.");
            return;
        }

        double totalAverage = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "", bottomStudent = "";

        Map<String, Integer> gradeDistribution = new HashMap<>();
        for (Student s : students) {
            double avg = s.calculateAverage();
            totalAverage += avg;

            if (avg > highest) {
                highest = avg;
                topStudent = s.getStudentID() + " (" + s.getName() + ")";
            }
            if (avg < lowest) {
                lowest = avg;
                bottomStudent = s.getStudentID() + " (" + s.getName() + ")";
            }

            String grade = s.getLetterGrade(avg);
            gradeDistribution.put(grade, gradeDistribution.getOrDefault(grade, 0) + 1);
        }

        System.out.println("\n📊 Class Performance Summary");
        System.out.println("Total Students: " + students.size());
        System.out.printf("Overall Class Average: %.2f\n", totalAverage / students.size());
        System.out.printf("Highest Scorer: %s with %.2f\n", topStudent, highest);
        System.out.printf("Lowest Scorer: %s with %.2f\n", bottomStudent, lowest);

        System.out.println("\n📈 Grade Distribution:");
        for (String grade : gradeDistribution.keySet()) {
            System.out.println("Grade " + grade + ": " + gradeDistribution.get(grade));
        }
    }

    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentID().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
}

