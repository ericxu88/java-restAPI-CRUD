package com.example.live.party;

import com.example.live.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    
    // Find parties where user is a member
    @Query("SELECT p FROM Party p JOIN p.members m WHERE m = :user")
    List<Party> findPartiesByMember(User user);
    
    // Find parties owned by a user
    List<Party> findByOwner(User owner);
}