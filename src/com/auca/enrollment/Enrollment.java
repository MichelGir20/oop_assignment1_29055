package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.interfaces.*;
import com.auca.student.Student;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {
    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String semester;
    private String enrollmentStatus;

    public Enrollment(String enrollmentId, Student student, Course course,
                      LocalDate enrollmentDate, String semester, String enrollmentStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String enrollmentStatus) { this.enrollmentStatus = enrollmentStatus; }

    @Override public String getId() { return enrollmentId; }
    @Override public String getDisplayName() {
        return student.getDisplayName() + " → " + course.getCourseCode();
    }
    @Override public boolean isActive() { return "ACTIVE".equalsIgnoreCase(enrollmentStatus); }

    @Override
    public void validate() throws ValidationException {
        if (enrollmentId == null || enrollmentId.trim().isEmpty())
            throw new ValidationException("Enrollment ID required.");
        if (student == null) throw new ValidationException("Student required.");
        if (course == null) throw new ValidationException("Course required.");
        if (enrollmentDate == null || enrollmentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Enrollment date cannot be in the future.");
        if (semester == null || semester.trim().isEmpty())
            throw new ValidationException("Semester required.");
        if (enrollmentStatus == null ||
            !(enrollmentStatus.equalsIgnoreCase("ACTIVE") || enrollmentStatus.equalsIgnoreCase("COMPLETED")
              || enrollmentStatus.equalsIgnoreCase("DROPPED")))
            throw new ValidationException("Status must be ACTIVE / COMPLETED / DROPPED.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Enrollment ----");
        System.out.println("ID: " + enrollmentId);
        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Course: " + course.getCourseCode());
        System.out.println("Date: " + enrollmentDate);
        System.out.println("Semester: " + semester);
        System.out.println("Status: " + enrollmentStatus);
    }

    @Override
    public String getRegistrationSummary() {
        return "Enrollment [" + enrollmentId + "] " + student.getDisplayName()
               + " in " + course.getCourseCode() + " (" + semester + ", " + enrollmentStatus + ")";
    }

    @Override public String toString() { return getRegistrationSummary(); }
}