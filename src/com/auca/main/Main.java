package com.auca.main;

import com.auca.abstracts.*;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.*;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.*;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Single shared Scanner
    private static final Scanner scanner = new Scanner(System.in);

    // In-memory collections
    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> feePayments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Select an option: ", 0, 19);
            switch (choice) {
                case 1: registerDepartment(); break;
                case 2: registerStudent(); break;
                case 3: registerTeacher(); break;
                case 4: registerAdministrator(); break;
                case 5: registerCourse(); break;
                case 6: registerClassroom(); break;
                case 7: enrollStudent(); break;
                case 8: recordAssessment(); break;
                case 9: recordFeePayment(); break;
                case 10: registerLibraryBook(); break;
                case 11: borrowBook(); break;
                case 12: returnBook(); break;
                case 13: viewAllRecords(); break;
                case 14: searchById(); break;
                case 15: updateRecord(); break;
                case 16: showStudentAcademicSummary(); break;
                case 17: showPaymentHistory(); break;
                case 18: showAvailableBooks(); break;
                case 19: demonstratePolymorphism(); break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== AUCA_ID SCHOOL MANAGEMENT =====");
        System.out.println("1. Register Department");
        System.out.println("2. Register Student");
        System.out.println("3. Register Teacher");
        System.out.println("4. Register Administrator");
        System.out.println("5. Register Course");
        System.out.println("6. Register Classroom");
        System.out.println("7. Enroll Student");
        System.out.println("8. Record Assessment");
        System.out.println("9. Record Fee Payment");
        System.out.println("10. Register Library Book");
        System.out.println("11. Borrow Book");
        System.out.println("12. Return Book");
        System.out.println("13. View All Records");
        System.out.println("14. Search by ID");
        System.out.println("15. Update Record");
        System.out.println("16. Show Student Academic Summary");
        System.out.println("17. Show Payment History");
        System.out.println("18. Show Available Books");
        System.out.println("19. Demonstrate Polymorphism");
        System.out.println("0. Exit");
    }

    // ===================== INPUT HELPERS =====================

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n < min || n > max) {
                    System.out.println("! Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return n;
            } catch (NumberFormatException e) {
                System.out.println("! Invalid number. Try again.");
            }
        }
    }

    private static String readText(String prompt, int minLen, int maxLen) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equals("0")) return null; // cancel
            if (s.length() < minLen || s.length() > maxLen) {
                System.out.println("! Must be " + minLen + "-" + maxLen + " chars.");
                continue;
            }
            return s;
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equals("0")) return null;
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date. Use YYYY-MM-DD (e.g., 2004-05-16).");
            }
        }
    }

    private static BigDecimal readMoney(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (s.equals("0")) return null;
            try {
                BigDecimal b = new BigDecimal(s);
                if (b.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("! Amount must be > 0.");
                    continue;
                }
                return b;
            } catch (NumberFormatException e) {
                System.out.println("! Invalid amount.");
            }
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (1 YES / 2 NO): ");
            String s = scanner.nextLine().trim();
            if (s.equals("1")) return true;
            if (s.equals("2")) return false;
            System.out.println("! Choose 1 or 2.");
        }
    }

    private static void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }

    // ===================== DEPARTMENT =====================

    private static Department registerDepartment() {
        System.out.println("\n--- REGISTER DEPARTMENT ---");
        String id = readText("Department ID (0=cancel): ", 3, 30);
        if (id == null) return null;
        if (findDepartmentById(id) != null) {
            System.out.println("! Department ID already exists.");
            return null;
        }
        String name = readText("Name: ", 2, 60);
        if (name == null) return null;
        String code = readText("Code: ", 2, 20);
        if (code == null) return null;
        String office = readText("Office location: ", 2, 60);
        if (office == null) return null;
        String email = readText("Contact email: ", 5, 100);
        if (email == null) return null;

        Department d = new Department(id, name, code, office, email, null);
        try {
            d.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return null;
        }
        departments.add(d);
        System.out.println("✓ Department registered: " + d.getRegistrationSummary());
        return d;
    }

    private static Department findDepartmentById(String id) {
        for (Department d : departments) if (d.getId().equals(id)) return d;
        return null;
    }

    // ===================== STUDENT =====================

    private static void registerStudent() {
        System.out.println("\n--- REGISTER STUDENT ---");

        if (departments.isEmpty()) {
            System.out.println("No Department exists. Register one first.");
            System.out.print("1 Register Department now / 0 Cancel: ");
            String ch = scanner.nextLine().trim();
            if (!ch.equals("1") || registerDepartment() == null) return;
        }

        Department dept = chooseDepartment();
        if (dept == null) { System.out.println("Cancelled."); return; }

        String personId = readText("Person ID: ", 3, 30);
        if (personId == null) return;
        if (findStudentByPersonId(personId) != null) {
            System.out.println("! Person ID already exists.");
            return;
        }
        String fn = readText("First name: ", 2, 60);
        if (fn == null) return;
        String ln = readText("Last name: ", 2, 60);
        if (ln == null) return;
        String email = readText("Email: ", 5, 100);
        if (email == null) return;
        String phone = readText("Phone (10-15 digits, optional +): ", 10, 16);
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");
        if (dob == null) return;

        String memberNo = readText("Member number: ", 2, 30);
        if (memberNo == null) return;
        LocalDate joinDate = readDate("Join date (YYYY-MM-DD): ");
        if (joinDate == null) return;
        String status = readText("Academic status (ACTIVE/INACTIVE): ", 6, 8);
        if (status == null) return;
        String instEmail = readText("Institutional email: ", 5, 100);
        if (instEmail == null) return;
        String advisor = readText("Advisor name: ", 2, 120);
        if (advisor == null) return;

        String stuNo = readText("Student number: ", 2, 30);
        if (stuNo == null) return;
        if (findStudentByStudentNumber(stuNo) != null) {
            System.out.println("! Student number already exists.");
            return;
        }
        String program = readText("Program name: ", 2, 120);
        if (program == null) return;
        LocalDate admission = readDate("Admission date (YYYY-MM-DD): ");
        if (admission == null) return;
        int year = readInt("Study year (1-8): ", 1, 8);
        String gPhone = readText("Guardian phone: ", 10, 16);
        if (gPhone == null) return;
        boolean scholarship = readYesNo("Scholarship? ");

        Student s = new Student(personId, fn, ln, email, phone, dob,
                                memberNo, dept, joinDate, status, instEmail, advisor,
                                stuNo, program, admission, year, gPhone, scholarship);
        try {
            s.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        students.add(s);
        System.out.println("✓ Student registered: " + s.getRegistrationSummary());
    }

    private static Student findStudentByPersonId(String id) {
        for (Student s : students) if (s.getId().equals(id)) return s;
        return null;
    }

    private static Student findStudentByStudentNumber(String num) {
        for (Student s : students) if (s.getStudentNumber().equals(num)) return s;
        return null;
    }

    private static Department chooseDepartment() {
        if (departments.isEmpty()) return null;
        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++)
            System.out.println((i + 1) + ". " + departments.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int c = readInt("Select: ", 0, departments.size());
        if (c == 0) return null;
        return departments.get(c - 1);
    }

    // ===================== TEACHER =====================

    private static void registerTeacher() {
        System.out.println("\n--- REGISTER TEACHER ---");

        if (departments.isEmpty()) {
            System.out.println("No Department exists. Register one first.");
            System.out.print("1 Register Department now / 0 Cancel: ");
            String ch = scanner.nextLine().trim();
            if (!ch.equals("1") || registerDepartment() == null) return;
        }

        Department dept = chooseDepartment();
        if (dept == null) { System.out.println("Cancelled."); return; }

        String personId = readText("Person ID: ", 3, 30);
        if (personId == null) return;
        for (Teacher t : teachers) if (t.getId().equals(personId)) {
            System.out.println("! Person ID already exists.");
            return;
        }
        String fn = readText("First name: ", 2, 60);
        if (fn == null) return;
        String ln = readText("Last name: ", 2, 60);
        if (ln == null) return;
        String email = readText("Email: ", 5, 100);
        if (email == null) return;
        String phone = readText("Phone: ", 10, 16);
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");
        if (dob == null) return;

        String memberNo = readText("Member number: ", 2, 30);
        if (memberNo == null) return;
        LocalDate joinDate = readDate("Join date: ");
        if (joinDate == null) return;
        String status = readText("Academic status (ACTIVE/INACTIVE): ", 6, 8);
        if (status == null) return;
        String instEmail = readText("Institutional email: ", 5, 100);
        if (instEmail == null) return;
        String advisor = readText("Advisor name: ", 2, 120);
        if (advisor == null) return;

        String empNo = readText("Employee number: ", 2, 30);
        if (empNo == null) return;
        String spec = readText("Specialization: ", 2, 60);
        if (spec == null) return;
        LocalDate hire = readDate("Hire date: ");
        if (hire == null) return;
        BigDecimal salary = readMoney("Salary (>0): ");
        if (salary == null) return;
        String office = readText("Office number: ", 1, 20);
        if (office == null) return;
        String empType = readText("Employment type: ", 2, 30);
        if (empType == null) return;

        Teacher t = new Teacher(personId, fn, ln, email, phone, dob,
                                memberNo, dept, joinDate, status, instEmail, advisor,
                                empNo, spec, hire, salary, office, empType);
        try {
            t.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        teachers.add(t);
        System.out.println("✓ Teacher registered: " + t.getRegistrationSummary());
    }

    // ===================== ADMINISTRATOR =====================

    private static void registerAdministrator() {
        System.out.println("\n--- REGISTER ADMINISTRATOR ---");
        String personId = readText("Person ID: ", 3, 30);
        if (personId == null) return;
        for (Administrator a : administrators) if (a.getId().equals(personId)) {
            System.out.println("! Person ID already exists.");
            return;
        }
        String fn = readText("First name: ", 2, 60);
        if (fn == null) return;
        String ln = readText("Last name: ", 2, 60);
        if (ln == null) return;
        String email = readText("Email: ", 5, 100);
        if (email == null) return;
        String phone = readText("Phone: ", 10, 16);
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth: ");
        if (dob == null) return;

        String staffNo = readText("Staff number: ", 2, 30);
        if (staffNo == null) return;
        String jobTitle = readText("Job title: ", 2, 60);
        if (jobTitle == null) return;
        String office = readText("Assigned office: ", 1, 30);
        if (office == null) return;
        LocalDate empDate = readDate("Employment date: ");
        if (empDate == null) return;
        int access = readInt("Access level (1-10): ", 1, 10);
        boolean active = readYesNo("Active? ");

        Administrator a = new Administrator(personId, fn, ln, email, phone, dob,
                                            staffNo, jobTitle, office, empDate, access, active);
        try {
            a.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        administrators.add(a);
        System.out.println("✓ Administrator registered: " + a.getRegistrationSummary());
    }

    // ===================== COURSE =====================

    private static void registerCourse() {
        System.out.println("\n--- REGISTER COURSE ---");
        if (departments.isEmpty()) { System.out.println("No Department exists."); return; }
        if (teachers.isEmpty()) { System.out.println("No Teacher exists."); return; }

        Department dept = chooseDepartment();
        if (dept == null) return;
        Teacher teacher = chooseTeacher();
        if (teacher == null) return;

        String id = readText("Course ID: ", 3, 30);
        if (id == null) return;
        for (Course c : courses) if (c.getId().equals(id)) {
            System.out.println("! Course ID exists.");
            return;
        }
        String code = readText("Course code: ", 2, 20);
        if (code == null) return;
        String title = readText("Course title: ", 2, 120);
        if (title == null) return;
        int credits = readInt("Credit hours (1-6): ", 1, 6);

        Course c = new Course(id, code, title, credits, dept, teacher);
        try {
            c.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        courses.add(c);
        System.out.println("✓ Course registered: " + c.getRegistrationSummary());
    }

    private static Teacher chooseTeacher() {
        if (teachers.isEmpty()) return null;
        System.out.println("Select Teacher:");
        for (int i = 0; i < teachers.size(); i++)
            System.out.println((i + 1) + ". " + teachers.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int c = readInt("Select: ", 0, teachers.size());
        if (c == 0) return null;
        return teachers.get(c - 1);
    }

    // ===================== CLASSROOM =====================

    private static void registerClassroom() {
        System.out.println("\n--- REGISTER CLASSROOM ---");
        String id = readText("Classroom ID: ", 3, 30);
        if (id == null) return;
        for (Classroom c : classrooms) if (c.getId().equals(id)) {
            System.out.println("! Classroom ID exists.");
            return;
        }
        String building = readText("Building name: ", 2, 60);
        if (building == null) return;
        String room = readText("Room number: ", 1, 20);
        if (room == null) return;
        int capacity = readInt("Capacity (>0): ", 1, 10000);
        int floor = readInt("Floor (>=0): ", 0, 200);
        boolean available = readYesNo("Available? ");

        Classroom c = new Classroom(id, building, room, capacity, floor, available);
        try {
            c.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        classrooms.add(c);
        System.out.println("✓ Classroom registered: " + c.getRegistrationSummary());
    }

    // ===================== ENROLLMENT =====================

    private static void enrollStudent() {
        System.out.println("\n--- ENROLL STUDENT ---");
        if (students.isEmpty()) { System.out.println("No Student exists."); return; }
        if (courses.isEmpty()) { System.out.println("No Course exists."); return; }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int sc = readInt("Select: ", 0, students.size());
        if (sc == 0) return;
        Student s = students.get(sc - 1);

        System.out.println("Select Course:");
        for (int i = 0; i < courses.size(); i++)
            System.out.println((i + 1) + ". " + courses.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int cc = readInt("Select: ", 0, courses.size());
        if (cc == 0) return;
        Course c = courses.get(cc - 1);

        String semester = readText("Semester: ", 2, 20);
        if (semester == null) return;

        // duplicate active enrollment check
        for (Enrollment e : enrollments) {
            if (e.isActive()
                && e.getStudent().getId().equals(s.getId())
                && e.getCourse().getId().equals(c.getId())
                && e.getSemester().equalsIgnoreCase(semester)) {
                System.out.println("! Student already enrolled in this course this semester.");
                return;
            }
        }

        String id = readText("Enrollment ID: ", 3, 30);
        if (id == null) return;
        String status = readText("Status (ACTIVE/COMPLETED/DROPPED): ", 6, 9);
        if (status == null) return;

        Enrollment e = new Enrollment(id, s, c, LocalDate.now(), semester, status);
        try {
            e.validate();
        } catch (ValidationException ex) {
            System.out.println("! " + ex.getMessage());
            return;
        }
        enrollments.add(e);
        System.out.println("✓ Enrolled: " + e.getRegistrationSummary());
    }

    // ===================== ASSESSMENT =====================

    private static void recordAssessment() {
        System.out.println("\n--- RECORD ASSESSMENT ---");
        if (enrollments.isEmpty()) { System.out.println("No Enrollment exists."); return; }

        System.out.println("Select Enrollment:");
        for (int i = 0; i < enrollments.size(); i++)
            System.out.println((i + 1) + ". " + enrollments.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int ec = readInt("Select: ", 0, enrollments.size());
        if (ec == 0) return;
        Enrollment e = enrollments.get(ec - 1);
        if (!e.isActive()) { System.out.println("! Only ACTIVE enrollments can be assessed."); return; }

        String id = readText("Assessment ID: ", 3, 30);
        if (id == null) return;
        String type = readText("Type (QUIZ/EXAM/ASSIGNMENT/PROJECT): ", 4, 10);
        if (type == null) return;
        double max = 0;
        while (true) {
            System.out.print("Maximum score (>0): ");
            try {
                max = Double.parseDouble(scanner.nextLine().trim());
                if (max > 0) break;
            } catch (NumberFormatException ex) { }
            System.out.println("! Invalid. Must be > 0.");
        }
        double obtained = -1;
        while (true) {
            System.out.print("Obtained score (0-" + max + "): ");
            try {
                obtained = Double.parseDouble(scanner.nextLine().trim());
                if (obtained >= 0 && obtained <= max) break;
            } catch (NumberFormatException ex) { }
            System.out.println("! Invalid. Must be between 0 and " + max + ".");
        }

        Assessment a = new Assessment(id, e, type, max, obtained, LocalDate.now());
        try {
            a.validate();
        } catch (ValidationException ex) {
            System.out.println("! " + ex.getMessage());
            return;
        }
        assessments.add(a);
        System.out.println("✓ Assessment recorded: " + a.getRegistrationSummary());
    }

    // ===================== FEE PAYMENT =====================

    private static void recordFeePayment() {
        System.out.println("\n--- RECORD FEE PAYMENT ---");
        if (students.isEmpty()) { System.out.println("No Student exists."); return; }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int sc = readInt("Select: ", 0, students.size());
        if (sc == 0) return;
        Student s = students.get(sc - 1);

        String id = readText("Payment ID: ", 3, 30);
        if (id == null) return;
        BigDecimal amount = readMoney("Amount (>0): ");
        if (amount == null) return;
        String method = readText("Method (CASH/CARD/BANK_TRANSFER/MOBILE_MONEY): ", 4, 14);
        if (method == null) return;
        String receipt = readText("Receipt number: ", 2, 40);
        if (receipt == null) return;

        // duplicate receipt
        for (FeePayment p : feePayments) {
            if (p.getReceiptNumber().equalsIgnoreCase(receipt)) {
                System.out.println("! Receipt number already exists.");
                return;
            }
        }

        FeePayment p = new FeePayment(id, s, amount, LocalDate.now(), method, receipt);
        try {
            p.validate();
        } catch (ValidationException ex) {
            System.out.println("! " + ex.getMessage());
            return;
        }
        feePayments.add(p);
        System.out.println("✓ Payment recorded: " + p.getRegistrationSummary());
    }

    // ===================== LIBRARY BOOK =====================

    private static void registerLibraryBook() {
        System.out.println("\n--- REGISTER LIBRARY BOOK ---");
        String id = readText("Book ID: ", 3, 30);
        if (id == null) return;
        for (LibraryBook b : books) if (b.getId().equals(id)) {
            System.out.println("! Book ID exists.");
            return;
        }
        String isbn = readText("ISBN: ", 5, 20);
        if (isbn == null) return;
        String title = readText("Title: ", 2, 120);
        if (title == null) return;
        String author = readText("Author: ", 2, 80);
        if (author == null) return;
        int total = readInt("Total copies: ", 0, 10000);
        int avail = readInt("Available copies (<= total): ", 0, total);

        LibraryBook b = new LibraryBook(id, isbn, title, author, total, avail);
        try {
            b.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        books.add(b);
        System.out.println("✓ Book registered: " + b.getRegistrationSummary());
    }

    // ===================== BORROW / RETURN =====================

    private static void borrowBook() {
        System.out.println("\n--- BORROW BOOK ---");
        if (students.isEmpty()) { System.out.println("No Student exists."); return; }
        if (books.isEmpty()) { System.out.println("No Book exists."); return; }

        Student s;
        while (true) {
            System.out.println("Select Student:");
            for (int i = 0; i < students.size(); i++)
                System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
            System.out.println("0. Cancel");
            int sc = readInt("Select: ", 0, students.size());
            if (sc == 0) return;
            s = students.get(sc - 1);
            break;
        }

        LibraryBook b;
        while (true) {
            System.out.println("Select Book:");
            for (int i = 0; i < books.size(); i++)
                System.out.println((i + 1) + ". " + books.get(i).getRegistrationSummary());
            System.out.println("0. Cancel");
            int bc = readInt("Select: ", 0, books.size());
            if (bc == 0) return;
            b = books.get(bc - 1);
            if (b.getAvailableCopies() <= 0) {
                System.out.println("! BookUnavailable: no copies remaining. Pick another.");
                continue;
            }
            break;
        }

        String id = readText("Loan ID: ", 3, 30);
        if (id == null) return;
        LocalDate borrowed = readDate("Borrowed date: ");
        if (borrowed == null) return;
        LocalDate due = readDate("Due date: ");
        if (due == null || !due.isAfter(borrowed)) {
            System.out.println("! Due date must be after borrowed date.");
            return;
        }

        BookLoan loan = new BookLoan(id, s, b, borrowed, due, null);
        try {
            loan.validate();
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
            return;
        }
        // Only now change stock
        b.setAvailableCopies(b.getAvailableCopies() - 1);
        loans.add(loan);
        System.out.println("✓ Book loaned: " + loan.getRegistrationSummary());
    }

    private static void returnBook() {
        System.out.println("\n--- RETURN BOOK ---");
        List<BookLoan> openLoans = new ArrayList<>();
        for (BookLoan l : loans) if (l.isActive()) openLoans.add(l);
        if (openLoans.isEmpty()) { System.out.println("No open loans."); return; }

        for (int i = 0; i < openLoans.size(); i++)
            System.out.println((i + 1) + ". " + openLoans.get(i).getRegistrationSummary());
        System.out.println("0. Cancel");
        int c = readInt("Select loan: ", 0, openLoans.size());
        if (c == 0) return;
        BookLoan loan = openLoans.get(c - 1);

        LocalDate ret = readDate("Returned date: ");
        if (ret == null) return;
        if (ret.isBefore(loan.getBorrowedDate())) {
            System.out.println("! Returned date cannot be before borrowed date.");
            return;
        }
        loan.setReturnedDate(ret);
        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        System.out.println("✓ Returned: " + loan.getRegistrationSummary());
    }

    // ===================== VIEW / SEARCH / UPDATE =====================

    private static void viewAllRecords() {
        System.out.println("\n--- ALL RECORDS ---");
        System.out.println("Departments:");     for (Department d : departments) System.out.println("  " + d.getRegistrationSummary());
        System.out.println("Students:");        for (Student s : students) System.out.println("  " + s.getRegistrationSummary());
        System.out.println("Teachers:");        for (Teacher t : teachers) System.out.println("  " + t.getRegistrationSummary());
        System.out.println("Administrators:");  for (Administrator a : administrators) System.out.println("  " + a.getRegistrationSummary());
        System.out.println("Courses:");         for (Course c : courses) System.out.println("  " + c.getRegistrationSummary());
        System.out.println("Classrooms:");      for (Classroom c : classrooms) System.out.println("  " + c.getRegistrationSummary());
        System.out.println("Enrollments:");     for (Enrollment e : enrollments) System.out.println("  " + e.getRegistrationSummary());
        System.out.println("Assessments:");     for (Assessment a : assessments) System.out.println("  " + a.getRegistrationSummary());
        System.out.println("Fee Payments:");    for (FeePayment p : feePayments) System.out.println("  " + p.getRegistrationSummary());
        System.out.println("Books:");           for (LibraryBook b : books) System.out.println("  " + b.getRegistrationSummary());
        System.out.println("Loans:");           for (BookLoan l : loans) System.out.println("  " + l.getRegistrationSummary());
    }

    private static void searchById() {
        System.out.println("\n--- SEARCH BY ID ---");
        System.out.println("1 Department 2 Student 3 Teacher 4 Administrator 5 Course");
        System.out.println("6 Classroom 7 Enrollment 8 Assessment 9 FeePayment 10 Book 11 Loan");
        int t = readInt("Entity type: ", 1, 11);
        String id = readText("ID: ", 1, 40);
        if (id == null) return;
        switch (t) {
            case 1: for (Department d : departments) if (d.getId().equals(id)) { d.printDetails(); pause(); return; } break;
            case 2: for (Student s : students) if (s.getId().equals(id)) { s.printDetails(); pause(); return; } break;
            case 3: for (Teacher te : teachers) if (te.getId().equals(id)) { te.printDetails(); pause(); return; } break;
            case 4: for (Administrator a : administrators) if (a.getId().equals(id)) { a.printDetails(); pause(); return; } break;
            case 5: for (Course c : courses) if (c.getId().equals(id)) { c.printDetails(); pause(); return; } break;
            case 6: for (Classroom c : classrooms) if (c.getId().equals(id)) { c.printDetails(); pause(); return; } break;
            case 7: for (Enrollment e : enrollments) if (e.getId().equals(id)) { e.printDetails(); pause(); return; } break;
            case 8: for (Assessment a : assessments) if (a.getId().equals(id)) { a.printDetails(); pause(); return; } break;
            case 9: for (FeePayment p : feePayments) if (p.getId().equals(id)) { p.printDetails(); pause(); return; } break;
            case 10: for (LibraryBook b : books) if (b.getId().equals(id)) { b.printDetails(); pause(); return; } break;
            case 11: for (BookLoan l : loans) if (l.getId().equals(id)) { l.printDetails(); pause(); return; } break;
        }
        System.out.println("! Record not found.");
    }

    private static void updateRecord() {
        System.out.println("\n--- UPDATE RECORD ---");
        System.out.println("1 Student 2 Teacher 3 Department");
        int t = readInt("Select type: ", 1, 3);
        String id = readText("Enter ID to update: ", 1, 40);
        if (id == null) return;
        if (t == 1) {
            for (Student s : students) if (s.getId().equals(id)) {
                System.out.println("Current: " + s.getRegistrationSummary());
                System.out.println("1 First name 2 Last name 3 Email 4 Phone");
                int f = readInt("Field to edit: ", 1, 4);
                String newVal = readText("New value: ", 1, 120);
                if (newVal == null) return;
                if (f == 1) s.setFirstName(newVal);
                else if (f == 2) s.setLastName(newVal);
                else if (f == 3) s.setEmail(newVal);
                else s.setPhoneNumber(newVal);
                try { s.validate(); System.out.println("✓ Updated."); }
                catch (ValidationException e) { System.out.println("! " + e.getMessage()); }
                return;
            }
        } else if (t == 2) {
            for (Teacher te : teachers) if (te.getId().equals(id)) {
                System.out.println("Current: " + te.getRegistrationSummary());
                System.out.println("1 First name 2 Last name 3 Email 4 Phone");
                int f = readInt("Field: ", 1, 4);
                String newVal = readText("New value: ", 1, 120);
                if (newVal == null) return;
                if (f == 1) te.setFirstName(newVal);
                else if (f == 2) te.setLastName(newVal);
                else if (f == 3) te.setEmail(newVal);
                else te.setPhoneNumber(newVal);
                try { te.validate(); System.out.println("✓ Updated."); }
                catch (ValidationException e) { System.out.println("! " + e.getMessage()); }
                return;
            }
        } else {
            for (Department d : departments) if (d.getId().equals(id)) {
                System.out.println("Current: " + d.getRegistrationSummary());
                System.out.println("1 Name 2 Office 3 Contact email");
                int f = readInt("Field: ", 1, 3);
                String newVal = readText("New value: ", 1, 120);
                if (newVal == null) return;
                if (f == 1) d.setDepartmentName(newVal);
                else if (f == 2) d.setOfficeLocation(newVal);
                else d.setContactEmail(newVal);
                try { d.validate(); System.out.println("✓ Updated."); }
                catch (ValidationException e) { System.out.println("! " + e.getMessage()); }
                return;
            }
        }
        System.out.println("! Record not found.");
    }

    // ===================== REPORTS =====================

    private static void showStudentAcademicSummary() {
        if (students.isEmpty()) { System.out.println("No students."); return; }
        Student s;
        while (true) {
            for (int i = 0; i < students.size(); i++)
                System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
            int c = readInt("Select student (0 cancel): ", 0, students.size());
            if (c == 0) return;
            s = students.get(c - 1); break;
        }
        System.out.println("\n--- Academic Summary for " + s.getDisplayName() + " ---");
        boolean any = false;
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(s.getId())) {
                any = true;
                System.out.println("Course: " + e.getCourse().getCourseCode() + " (" + e.getSemester() + ")");
                for (Assessment a : assessments) {
                    if (a.getEnrollment().getId().equals(e.getId())) {
                        System.out.println("   - " + a.getAssessmentType() + ": " + a.getObtainedScore() + "/" + a.getMaximumScore());
                    }
                }
            }
        }
        if (!any) System.out.println("No enrollments.");
    }

    private static void showPaymentHistory() {
        if (students.isEmpty()) { System.out.println("No students."); return; }
        Student s;
        while (true) {
            for (int i = 0; i < students.size(); i++)
                System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
            int c = readInt("Select student: ", 0, students.size());
            if (c == 0) return;
            s = students.get(c - 1); break;
        }
        BigDecimal total = BigDecimal.ZERO;
        System.out.println("\n--- Payment history for " + s.getDisplayName() + " ---");
        for (FeePayment p : feePayments) {
            if (p.getStudent().getId().equals(s.getId())) {
                System.out.println(p.getRegistrationSummary() + " | " + p.getPaymentDate());
                total = total.add(p.getAmount());
            }
        }
        System.out.println("Total paid: " + total);
    }

    private static void showAvailableBooks() {
        System.out.println("\n--- AVAILABLE BOOKS ---");
        boolean any = false;
        for (LibraryBook b : books) {
            if (b.getAvailableCopies() > 0) {
                System.out.println(b.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("None.");
    }

    // ===================== POLYMORPHISM =====================

    private static void demonstratePolymorphism() {
        System.out.println("\n--- POLYMORPHISM DEMO ---");
        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        if (people.isEmpty()) { System.out.println("No people to show."); return; }

        System.out.println("Via List<Person>:");
        for (Person p : people) {
            System.out.println("  " + p.getRoleDescription() + " → " + p.getDisplayName());
        }

        List<Identifiable> anything = new ArrayList<>();
        anything.addAll(departments);
        anything.addAll(courses);
        anything.addAll(books);
        if (!anything.isEmpty()) {
            System.out.println("Via List<Identifiable>:");
            for (Identifiable i : anything) {
                System.out.println("  [" + i.getId() + "] " + i.getDisplayName()
                                   + " active=" + i.isActive());
            }
        }
    }
}