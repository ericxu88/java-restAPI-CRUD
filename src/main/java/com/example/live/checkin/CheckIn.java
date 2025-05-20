package com.example.live.checkin;

import com.example.live.habit.Habit;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "check_ins")
public class CheckIn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name = "notes")
    private String notes;
    
    // Enum for check-in status
    public enum Status {
        DONE, MISSED
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}