package com.example.julia.homeworkcalendar;

import java.util.Calendar;

/**
 * Created by julia on 4/12/2018.
 */

public class CalendarObject {
    private String DueDate;
    private String ClassAssigned;
    private String Description;
    private Boolean reminder;
    private String AssignedDate;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }




    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getClassAssigned() {
        return ClassAssigned;
    }

    public void setClassAssigned(String classAssigned) {
        ClassAssigned = classAssigned;
    }

    public String getAssignedDate() {
        return AssignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        AssignedDate = assignedDate;
    }


}
