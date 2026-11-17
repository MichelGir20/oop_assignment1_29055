package com.auca.feepayment;

import com.auca.interfaces.*;
import com.auca.student.Student;
import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class FeePayment implements Identifiable, Registrable {
    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String receiptNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount,
                      LocalDate paymentDate, String paymentMethod, String receiptNumber) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    @Override public String getId() { return paymentId; }
    @Override public String getDisplayName() { return "Payment " + receiptNumber; }
    @Override public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (paymentId == null || paymentId.trim().isEmpty())
            throw new ValidationException("Payment ID required.");
        if (student == null)
            throw new ValidationException("Student required.");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Amount must be strictly positive.");
        if (paymentDate == null || paymentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Payment date cannot be in the future.");
        if (paymentMethod == null ||
            !(paymentMethod.equalsIgnoreCase("CASH") || paymentMethod.equalsIgnoreCase("CARD")
              || paymentMethod.equalsIgnoreCase("BANK_TRANSFER") || paymentMethod.equalsIgnoreCase("MOBILE_MONEY")))
            throw new ValidationException("Method must be CASH/CARD/BANK_TRANSFER/MOBILE_MONEY.");
        if (receiptNumber == null || receiptNumber.trim().isEmpty())
            throw new ValidationException("Receipt number required.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Fee Payment ----");
        System.out.println("ID: " + paymentId);
        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Amount: " + amount);
        System.out.println("Date: " + paymentDate);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Receipt: " + receiptNumber);
    }

    @Override
    public String getRegistrationSummary() {
        return "Payment [" + paymentId + "] " + receiptNumber + " - " + amount
               + " (" + paymentMethod + ")";
    }

    @Override public String toString() { return getRegistrationSummary(); }
}