package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Student extends AcademicMember {

    // 6 declared attributes
    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department, LocalDate joinDate,
                   String academicStatus, String institutionalEmail, String advisorName,
                   String studentNumber, String programName, LocalDate admissionDate,
                   int studyYear, String guardianPhone, boolean scholarshipStatus) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
              memberNumber, department, joinDate, academicStatus,
              institutionalEmail, advisorName);
        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int studyYear) { this.studyYear = studyYear; }
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    public boolean isScholarshipStatus() { return scholarshipStatus; }
    public void setScholarshipStatus(boolean scholarshipStatus) { this.scholarshipStatus = scholarshipStatus; }

    @Override
    public String getRoleDescription() { return "Student"; }

    @Override
    public String getAcademicCategory() { return "Undergraduate"; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (studentNumber == null || studentNumber.trim().isEmpty())
            throw new ValidationException("Student number required.");
        if (programName == null || programName.length() < 2 || programName.length() > 120)
            throw new ValidationException("Program name must be 2-120 chars.");
        if (admissionDate == null || admissionDate.isAfter(LocalDate.now()))
            throw new ValidationException("Admission date cannot be in the future.");
        if (studyYear < 1 || studyYear > 8)
            throw new ValidationException("Study year must be 1-8.");
        if (guardianPhone == null || !guardianPhone.matches("^\\+?[0-9]{10,15}$"))
            throw new ValidationException("Guardian phone must be 10-15 digits.");
    }

    @Override
    public void printDetails() {
        super.printDetails();
        System.out.println("Student Number: " + studentNumber);
        System.out.println("Program: " + programName);
        System.out.println("Admission: " + admissionDate);
        System.out.println("Year: " + studyYear);
        System.out.println("Guardian Phone: " + guardianPhone);
        System.out.println("Scholarship: " + (scholarshipStatus ? "YES" : "NO"));
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary() + " | student#=" + studentNumber
                + " | program=" + programName + " | year=" + studyYear;
    }
}