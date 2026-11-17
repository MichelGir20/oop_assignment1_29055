package com.auca.course;

import com.auca.department.Department;
import com.auca.interfaces.*;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

public class Course implements Identifiable, Registrable {
    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle,
                  int creditHours, Department department, Teacher assignedTeacher) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.department = department;
        this.assignedTeacher = assignedTeacher;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher assignedTeacher) { this.assignedTeacher = assignedTeacher; }

    @Override public String getId() { return courseId; }
    @Override public String getDisplayName() { return courseTitle; }
    @Override public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (courseId == null || courseId.trim().isEmpty())
            throw new ValidationException("Course ID required.");
        if (courseCode == null || courseCode.trim().isEmpty())
            throw new ValidationException("Course code required.");
        if (courseTitle == null || courseTitle.length() < 2 || courseTitle.length() > 120)
            throw new ValidationException("Course title must be 2-120 chars.");
        if (creditHours < 1 || creditHours > 6)
            throw new ValidationException("Credit hours must be 1-6.");
        if (department == null)
            throw new ValidationException("Department required.");
        if (assignedTeacher == null)
            throw new ValidationException("Assigned teacher required.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Course ----");
        System.out.println("ID: " + courseId);
        System.out.println("Code: " + courseCode);
        System.out.println("Title: " + courseTitle);
        System.out.println("Credits: " + creditHours);
        System.out.println("Department: " + (department != null ? department.getDepartmentName() : "-"));
        System.out.println("Teacher: " + (assignedTeacher != null ? assignedTeacher.getDisplayName() : "-"));
    }

    @Override
    public String getRegistrationSummary() {
        return "Course [" + courseId + "] " + courseCode + " " + courseTitle + " (" + creditHours + " cr)";
    }

    @Override public String toString() { return getRegistrationSummary(); }
}