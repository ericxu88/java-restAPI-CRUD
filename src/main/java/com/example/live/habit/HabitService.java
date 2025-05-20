package com.example.live.habit;

import com.example.live.checkin.CheckIn;
import com.example.live.checkin.CheckInRepository;
import com.example.live.user.User;
import com.example.live.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CheckInRepository checkInRepository;
    private final UserRepository userRepository;

    public HabitService(
            HabitRepository habitRepository,
            CheckInRepository checkInRepository,
            UserRepository userRepository
    ) {
        this.habitRepository = habitRepository;
        this.checkInRepository = checkInRepository;
        this.userRepository = userRepository;
    }

    public List<Habit> getUserHabits(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return habitRepository.findByUser(user);
    }

    public Habit createHabit(Long userId, Habit habit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        habit.setUser(user);
        habit.setCurrentStreak(0);
        
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long habitId, Habit updatedHabit) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        habit.setTitle(updatedHabit.getTitle());
        habit.setCadence(updatedHabit.getCadence());
        habit.setDifficulty(updatedHabit.getDifficulty());
        habit.setTargetTime(updatedHabit.getTargetTime());
        
        return habitRepository.save(habit);
    }

    public void deleteHabit(Long habitId) {
        habitRepository.deleteById(habitId);
    }

    public CheckIn checkInHabit(Long habitId, CheckIn.Status status, String notes) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        LocalDate today = LocalDate.now();
        
        // Check if already checked in today
        Optional<CheckIn> existingCheckIn = checkInRepository.findByHabitAndDate(habit, today);
        
        if (existingCheckIn.isPresent()) {
            CheckIn checkIn = existingCheckIn.get();
            checkIn.setStatus(status);
            checkIn.setNotes(notes);
            return checkInRepository.save(checkIn);
        } else {
            CheckIn checkIn = new CheckIn();
            checkIn.setHabit(habit);
            checkIn.setDate(today);
            checkIn.setStatus(status);
            checkIn.setNotes(notes);
            
            // Update streak if marked as DONE
            if (status == CheckIn.Status.DONE) {
                habit.setCurrentStreak(habit.getCurrentStreak() + 1);
                habitRepository.save(habit);
            } else {
                habit.setCurrentStreak(0);
                habitRepository.save(habit);
            }
            
            return checkInRepository.save(checkIn);
        }
    }

    public List<CheckIn> getHabitHistory(Long habitId, LocalDate startDate, LocalDate endDate) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        return checkInRepository.findByHabitAndDateBetween(habit, startDate, endDate);
    }
}