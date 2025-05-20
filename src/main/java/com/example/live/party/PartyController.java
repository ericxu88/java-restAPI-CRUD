package com.example.live.party;

import com.example.live.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Party>> getUserParties(@PathVariable Long userId) {
        return ResponseEntity.ok(partyService.getUserParties(userId));
    }

    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody Map<String, Object> partyData) {
        Long ownerId = Long.valueOf(partyData.get("ownerId").toString());
        String partyName = (String) partyData.get("name");
        
        return ResponseEntity.ok(partyService.createParty(ownerId, partyName));
    }

    @PostMapping("/{partyId}/members")
    public ResponseEntity<Party> addMember(
            @PathVariable Long partyId,
            @RequestBody Map<String, Long> memberData
    ) {
        return ResponseEntity.ok(partyService.addMember(partyId, memberData.get("userId")));
    }

    @DeleteMapping("/{partyId}/members/{userId}")
    public ResponseEntity<Party> removeMember(
            @PathVariable Long partyId,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(partyService.removeMember(partyId, userId));
    }

    @GetMapping("/{partyId}/members")
    public ResponseEntity<Set<User>> getPartyMembers(@PathVariable Long partyId) {
        return ResponseEntity.ok(partyService.getPartyMembers(partyId));
    }

    @PutMapping("/{partyId}/hp")
    public ResponseEntity<Party> updatePartyHp(
            @PathVariable Long partyId,
            @RequestBody Map<String, Integer> hpData
    ) {
        return ResponseEntity.ok(partyService.updatePartyHp(partyId, hpData.get("hp")));
    }

    @DeleteMapping("/{partyId}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long partyId) {
        partyService.deleteParty(partyId);
        return ResponseEntity.ok().build();
    }
}