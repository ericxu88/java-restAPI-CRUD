package com.example.live.habit;

import com.example.live.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    
    // Find habits by user
    List<Habit> findByUser(User user);
    
    // Find habits by user and cadence
    List<Habit> findByUserAndCadence(User user, Habit.Cadence cadence);
}