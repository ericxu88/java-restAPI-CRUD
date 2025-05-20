package com.example.live.game;

import com.example.live.checkin.CheckIn;
import com.example.live.checkin.CheckInRepository;
import com.example.live.habit.Habit;
import com.example.live.habit.HabitRepository;
import com.example.live.party.Party;
import com.example.live.party.PartyRepository;
import com.example.live.quest.Boss;
import com.example.live.quest.BossRepository;
import com.example.live.reward.Reward;
import com.example.live.reward.RewardRepository;
import com.example.live.user.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameEngineService {

    private final PartyRepository partyRepository;
    private final HabitRepository habitRepository;
    private final CheckInRepository checkInRepository;
    private final BossRepository bossRepository;
    private final RewardRepository rewardRepository;
    private final Random random = new Random();

    public GameEngineService(
            PartyRepository partyRepository,
            HabitRepository habitRepository,
            CheckInRepository checkInRepository,
            BossRepository bossRepository,
            RewardRepository rewardRepository
    ) {
        this.partyRepository = partyRepository;
        this.habitRepository = habitRepository;
        this.checkInRepository = checkInRepository;
        this.bossRepository = bossRepository;
        this.rewardRepository = rewardRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    @Transactional
    public void processDailyGameTurn() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        // Process all parties
        List<Party> allParties = partyRepository.findAll();
        
        for (Party party : allParties) {
            processPartyTurn(party, yesterday);
        }
    }

    private void processPartyTurn(Party party, LocalDate date) {
        // Get all members' habits
        Set<User> members = party.getMembers();
        Set<Habit> allMemberHabits = new HashSet<>();
        
        for (User member : members) {
            allMemberHabits.addAll(habitRepository.findByUser(member));
        }
        
        // Using atomic variables to enable modification in lambda expressions
        AtomicBoolean allCompleted = new AtomicBoolean(true);
        AtomicInteger totalDamage = new AtomicInteger(0);
        
        for (Habit habit : allMemberHabits) {
            // Skip weekly habits that aren't due today
            if (habit.getCadence() == Habit.Cadence.WEEKLY && date.getDayOfWeek().getValue() != 1) {
                continue;
            }
            
            // Check if the habit was completed
            checkInRepository.findByHabitAndDate(habit, date)
                    .ifPresentOrElse(checkIn -> {
                        if (checkIn.getStatus() == CheckIn.Status.DONE) {
                            // Add damage based on difficulty
                            totalDamage.addAndGet(habit.getDifficulty() * (1 + habit.getCurrentStreak() / 10));
                        } else {
                            allCompleted.set(false);
                        }
                    }, () -> allCompleted.set(false));
        }
        
        // Process the turn based on completion
        if (allCompleted.get() && !allMemberHabits.isEmpty()) {
            // Party deals damage to boss
            Boss currentBoss = getCurrentBoss(party);
            if (currentBoss != null) {
                int newBossHp = Math.max(0, currentBoss.getHp() - totalDamage.get());
                currentBoss.setHp(newBossHp);
                bossRepository.save(currentBoss);
                
                // Check if boss is defeated
                if (newBossHp == 0) {
                    handleBossDefeat(party, currentBoss);
                }
            }
        } else {
            // Boss counter-attacks
            int damageToParty = calculateBossDamage(party);
            int newPartyHp = Math.max(0, party.getHp() - damageToParty);
            party.setHp(newPartyHp);
            partyRepository.save(party);
            
            // Check if party is defeated
            if (newPartyHp == 0) {
                // Party is "down" but not deleted
                // They could have a resurrection mechanic
            }
        }
    }

    private Boss getCurrentBoss(Party party) {
        // This is simplified - in a real app, you'd track which boss the party is fighting
        return bossRepository.findById(1L).orElse(null);
    }

    private int calculateBossDamage(Party party) {
        // Simple calculation - could be more complex in a real app
        Boss currentBoss = getCurrentBoss(party);
        return currentBoss != null ? currentBoss.getAttackPower() : 10;
    }

    private void handleBossDefeat(Party party, Boss boss) {
        // Award XP and gold to all party members
        for (User member : party.getMembers()) {
            // Give XP
            int xpReward = 50 + boss.getAttackPower() * 2;
            member.setXp(member.getXp() + xpReward);
            
            // Give gold
            int goldReward = 20 + boss.getAttackPower();
            member.setGold(member.getGold() + goldReward);
            
            // Chance for item reward
            if (random.nextInt(100) < 30) {  // 30% chance
                Reward itemReward = new Reward();
                itemReward.setUser(member);
                itemReward.setType(Reward.RewardType.ITEM);
                itemReward.setRarity(rollRarity());
                itemReward.setItemCode("item_" + random.nextInt(100)); // Just a placeholder
                rewardRepository.save(itemReward);
            }
        }
        
        // Move to next boss or world
        // This is simplified - in a real app, this would be more complex
    }

    private Reward.Rarity rollRarity() {
        int roll = random.nextInt(100);
        if (roll < 50) return Reward.Rarity.COMMON;
        if (roll < 80) return Reward.Rarity.UNCOMMON;
        if (roll < 95) return Reward.Rarity.RARE;
        if (roll < 99) return Reward.Rarity.EPIC;
        return Reward.Rarity.LEGENDARY;
    }
}