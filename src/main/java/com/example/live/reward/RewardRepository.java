package com.example.live.reward;

import com.example.live.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    
    // Find rewards by user
    List<Reward> findByUser(User user);
    
    // Find rewards by type
    List<Reward> findByType(Reward.RewardType type);
    
    // Find rewards by rarity
    List<Reward> findByRarity(Reward.Rarity rarity);
}