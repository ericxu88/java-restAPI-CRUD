package com.example.live.quest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestWorldRepository extends JpaRepository<QuestWorld, Long> {
    
    // Find worlds by level requirement
    QuestWorld findFirstByLevelRequirementLessThanEqualOrderByLevelRequirementDesc(Integer level);
}