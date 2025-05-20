package com.example.live.quest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BossRepository extends JpaRepository<Boss, Long> {
    
    // Find bosses by world
    List<Boss> findByWorld(QuestWorld world);
    
    // Find unlocked bosses
    List<Boss> findByUnlockDateLessThanEqual(LocalDate date);
}