package com.example.live.habit;

import com.example.live.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "habits")
public class Habit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "cadence", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cadence cadence;
    
    @Column(name = "difficulty")
    private Integer difficulty = 1; // 1-3 scale
    
    @Column(name = "target_time")
    private String targetTime; // Optional time of day
    
    @Column(name = "current_streak")
    private Integer currentStreak = 0;
    
    // Enum for habit frequency
    public enum Cadence {
        DAILY, WEEKLY
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cadence getCadence() {
        return cadence;
    }

    public void setCadence(Cadence cadence) {
        this.cadence = cadence;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }
}