package com.example.live.habit;

import com.example.live.checkin.CheckIn;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Habit>> getUserHabits(@PathVariable Long userId) {
        return ResponseEntity.ok(habitService.getUserHabits(userId));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Habit> createHabit(@PathVariable Long userId, @RequestBody Habit habit) {
        return ResponseEntity.ok(habitService.createHabit(userId, habit));
    }

    @PutMapping("/{habitId}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long habitId, @RequestBody Habit habit) {
        return ResponseEntity.ok(habitService.updateHabit(habitId, habit));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{habitId}/check-in")
    public ResponseEntity<CheckIn> checkInHabit(
            @PathVariable Long habitId,
            @RequestBody Map<String, Object> checkInData
    ) {
        CheckIn.Status status = CheckIn.Status.valueOf((String) checkInData.get("status"));
        String notes = (String) checkInData.getOrDefault("notes", "");
        
        return ResponseEntity.ok(habitService.checkInHabit(habitId, status, notes));
    }

    @GetMapping("/{habitId}/history")
    public ResponseEntity<List<CheckIn>> getHabitHistory(
            @PathVariable Long habitId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return ResponseEntity.ok(habitService.getHabitHistory(habitId, startDate, endDate));
    }
}