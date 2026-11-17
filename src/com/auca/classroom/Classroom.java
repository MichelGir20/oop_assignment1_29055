package com.auca.classroom;

import com.auca.interfaces.*;
import com.auca.exceptions.ValidationException;

public class Classroom implements Identifiable, Registrable {
    private String classroomId;
    private String buildingName;
    private String roomNumber;
    private int capacity;
    private int floorNumber;
    private boolean available;

    public Classroom(String classroomId, String buildingName, String roomNumber,
                     int capacity, int floorNumber, boolean available) {
        this.classroomId = classroomId;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.floorNumber = floorNumber;
        this.available = available;
    }

    public String getClassroomId() { return classroomId; }
    public void setClassroomId(String classroomId) { this.classroomId = classroomId; }
    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override public String getId() { return classroomId; }
    @Override public String getDisplayName() { return buildingName + " / " + roomNumber; }
    @Override public boolean isActive() { return available; }

    @Override
    public void validate() throws ValidationException {
        if (classroomId == null || classroomId.trim().isEmpty())
            throw new ValidationException("Classroom ID required.");
        if (buildingName == null || buildingName.trim().isEmpty())
            throw new ValidationException("Building name required.");
        if (roomNumber == null || roomNumber.trim().isEmpty())
            throw new ValidationException("Room number required.");
        if (capacity <= 0)
            throw new ValidationException("Capacity must be > 0.");
        if (floorNumber < 0)
            throw new ValidationException("Floor must be >= 0.");
    }

    @Override
    public void printDetails() {
        System.out.println("---- Classroom ----");
        System.out.println("ID: " + classroomId);
        System.out.println("Building: " + buildingName);
        System.out.println("Room: " + roomNumber);
        System.out.println("Capacity: " + capacity);
        System.out.println("Floor: " + floorNumber);
        System.out.println("Available: " + available);
    }

    @Override
    public String getRegistrationSummary() {
        return "Classroom [" + classroomId + "] " + buildingName + " / " + roomNumber;
    }

    @Override public String toString() { return getRegistrationSummary(); }
}