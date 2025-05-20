package com.example.live.party;

import com.example.live.user.User;
import com.example.live.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final UserRepository userRepository;

    public PartyService(PartyRepository partyRepository, UserRepository userRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
    }

    public List<Party> getUserParties(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return partyRepository.findPartiesByMember(user);
    }

    public Party createParty(Long ownerId, String partyName) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Party party = new Party();
        party.setName(partyName);
        party.setOwner(owner);
        party.setHp(100);
        party.addMember(owner);
        
        return partyRepository.save(party);
    }

    public Party addMember(Long partyId, Long userId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        party.addMember(user);
        return partyRepository.save(party);
    }

    public Party removeMember(Long partyId, Long userId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Cannot remove the owner
        if (party.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Cannot remove the party owner");
        }
        
        party.removeMember(user);
        return partyRepository.save(party);
    }

    public Set<User> getPartyMembers(Long partyId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        
        return party.getMembers();
    }

    public Party updatePartyHp(Long partyId, Integer newHp) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        
        party.setHp(newHp);
        return partyRepository.save(party);
    }

    public void deleteParty(Long partyId) {
        partyRepository.deleteById(partyId);
    }
}