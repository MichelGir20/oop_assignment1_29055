package com.auca.department;

import com.auca.interfaces.*;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

public class Department implements Identifiable, Registrable {

    // 6 declared attributes
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;   // optional at creation

    public Department(String departmentId, String departmentName, String departmentCode,
                      String officeLocation, String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }
    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public Teacher getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(Teacher headTeacher) { this.headTeacher = headTeacher; }

    @Override
    public String getId() { return departmentId; }
    @Override
    public String getDisplayName() { return departmentName; }
    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (departmentId == null || departmentId.trim().isEmpty())
            throw new ValidationException("Department ID required.");
        if (departmentName == null || departmentName.length() < 2 || departmentName.length() > 60)
            throw new ValidationException("Department name must be 2-60 chars.");
        if (departmentCode == null || departmentCode.trim().isEmpty())
            throw new ValidationException("Department code required.");
        if (officeLocation == null || officeLocation.trim().isEmpty())
            throw new ValidationException("Office location required.");
        if (contactEmail == null ||
            !contactEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new ValidationException("Invalid contact email.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Department ----");
        System.out.println("ID: " + departmentId);
        System.out.println("Name: " + departmentName);
        System.out.println("Code: " + departmentCode);
        System.out.println("Office: " + officeLocation);
        System.out.println("Email: " + contactEmail);
        System.out.println("Head: " + (headTeacher != null ? headTeacher.getDisplayName() : "(none)"));
    }

    @Override
    public String getRegistrationSummary() {
        return "Department [" + departmentId + "] " + departmentName + " (" + departmentCode + ")";
    }

    @Override
    public String toString() { return getRegistrationSummary(); }
}