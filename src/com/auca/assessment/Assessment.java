package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {
    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String assessmentType,
                      double maximumScore, double obtainedScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }
    public double getMaximumScore() { return maximumScore; }
    public void setMaximumScore(double maximumScore) { this.maximumScore = maximumScore; }
    public double getObtainedScore() { return obtainedScore; }
    public void setObtainedScore(double obtainedScore) { this.obtainedScore = obtainedScore; }
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }

    @Override public String getId() { return assessmentId; }
    @Override public String getDisplayName() { return assessmentType + " for " + enrollment.getId(); }
    @Override public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (assessmentId == null || assessmentId.trim().isEmpty())
            throw new ValidationException("Assessment ID required.");
        if (enrollment == null)
            throw new ValidationException("Enrollment required.");
        if (!enrollment.isActive())
            throw new ValidationException("Only active enrollments can be assessed.");
        if (assessmentType == null ||
            !(assessmentType.equalsIgnoreCase("QUIZ") || assessmentType.equalsIgnoreCase("EXAM")
              || assessmentType.equalsIgnoreCase("ASSIGNMENT") || assessmentType.equalsIgnoreCase("PROJECT")))
            throw new ValidationException("Type must be QUIZ/EXAM/ASSIGNMENT/PROJECT.");
        if (maximumScore <= 0)
            throw new ValidationException("Maximum score must be > 0.");
        if (obtainedScore < 0 || obtainedScore > maximumScore)
            throw new ValidationException("Obtained score must be between 0 and maximum.");
        if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now()))
            throw new ValidationException("Assessment date cannot be in the future.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Assessment ----");
        System.out.println("ID: " + assessmentId);
        System.out.println("Enrollment: " + enrollment.getId());
        System.out.println("Type: " + assessmentType);
        System.out.println("Max Score: " + maximumScore);
        System.out.println("Obtained: " + obtainedScore);
        System.out.println("Date: " + assessmentDate);
    }

    @Override
    public String getRegistrationSummary() {
        return "Assessment [" + assessmentId + "] " + assessmentType
               + " " + obtainedScore + "/" + maximumScore;
    }

    @Override public String toString() { return getRegistrationSummary(); }
}