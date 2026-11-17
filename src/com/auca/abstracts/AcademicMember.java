package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class AcademicMember extends Person {

    // 6 declared attributes
    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId, String firstName, String lastName,
                          String email, String phoneNumber, LocalDate dateOfBirth,
                          String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus,
                          String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    // Getters/Setters
    public String getMemberNumber() { return memberNumber; }
    public void setMemberNumber(String memberNumber) { this.memberNumber = memberNumber; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }
    public String getInstitutionalEmail() { return institutionalEmail; }
    public void setInstitutionalEmail(String institutionalEmail) { this.institutionalEmail = institutionalEmail; }
    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }

    // Abstract — Student/Teacher must provide
    public abstract String getAcademicCategory();

    @Override
    public void validate() throws ValidationException {
        super.validate(); // validate Person fields
        if (memberNumber == null || memberNumber.trim().isEmpty())
            throw new ValidationException("Member number is required.");
        if (department == null)
            throw new ValidationException("Department is required.");
        if (joinDate == null || joinDate.isAfter(LocalDate.now()))
            throw new ValidationException("Join date cannot be in the future.");
        if (academicStatus == null ||
            !(academicStatus.equalsIgnoreCase("ACTIVE") || academicStatus.equalsIgnoreCase("INACTIVE")))
            throw new ValidationException("Academic status must be ACTIVE or INACTIVE.");
        if (!isValidEmail(institutionalEmail))
            throw new ValidationException("Invalid institutional email.");
        if (advisorName == null || advisorName.length() < 2 || advisorName.length() > 120)
            throw new ValidationException("Advisor name must be 2-120 chars.");
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
            + " | member=" + memberNumber
            + " | dept=" + (department != null ? department.getDepartmentName() : "-")
            + " | status=" + academicStatus;
    }
}