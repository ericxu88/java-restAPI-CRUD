package com.example.live.checkin;

import com.example.live.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    
    // Find check-ins by habit
    List<CheckIn> findByHabit(Habit habit);
    
    // Find check-ins by habit and date
    Optional<CheckIn> findByHabitAndDate(Habit habit, LocalDate date);
    
    // Find check-ins by habit for date range
    List<CheckIn> findByHabitAndDateBetween(Habit habit, LocalDate start, LocalDate end);
}