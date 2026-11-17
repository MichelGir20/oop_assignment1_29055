package com.auca.abstracts;

import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {

    // 6 declared attributes
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    // ---- Getters / Setters ----
    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    // Abstract: concrete subclasses must supply
    public abstract String getRoleDescription();

    // Identifiable implementation
    @Override
    public String getId() { return personId; }

    @Override
    public String getDisplayName() { return firstName + " " + lastName; }

    @Override
    public boolean isActive() { return true; } // persons are active unless flagged otherwise

    // Registrable implementation
    @Override
    public void validate() throws ValidationException {
        if (isBlank(personId))
            throw new ValidationException("Person ID is required.");
        if (personId.length() < MIN_ID_LENGTH || personId.length() > MAX_ID_LENGTH)
            throw new ValidationException("Person ID must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " chars.");
        if (isBlank(firstName) || firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH)
            throw new ValidationException("First name must be 2-60 chars.");
        if (isBlank(lastName) || lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_NAME_LENGTH)
            throw new ValidationException("Last name must be 2-60 chars.");
        if (!isValidEmail(email))
            throw new ValidationException("Invalid email syntax.");
        if (email.length() > MAX_EMAIL_LENGTH)
            throw new ValidationException("Email too long.");
        if (!isValidPhone(phoneNumber))
            throw new ValidationException("Phone must be 10-15 digits, optional leading +.");
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now()))
            throw new ValidationException("Date of birth must be in the past.");
    }

    // Helper methods (protected so subclasses can reuse)
    protected boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    protected boolean isValidEmail(String e) {
        return e != null && e.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    protected boolean isValidPhone(String p) {
        return p != null && p.matches("^\\+?[0-9]{10,15}$");
    }

    @Override
    public String getRegistrationSummary() {
        return getRoleDescription() + " [" + personId + "] " + getDisplayName()
                + " | email=" + email + " | phone=" + phoneNumber;
    }

    @Override
    public void printDetails() {
        System.out.println("---- " + getRoleDescription() + " ----");
        System.out.println("ID: " + personId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("DOB: " + dateOfBirth);
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}