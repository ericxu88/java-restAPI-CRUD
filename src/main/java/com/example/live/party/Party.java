package com.example.live.party;

import com.example.live.user.User;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parties")
public class Party {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @Column(name = "hp")
    private Integer hp = 100; // Default health points
    
    @Column(name = "current_world_id")
    private Long currentWorldId;
    
    @ManyToMany
    @JoinTable(
        name = "party_members",
        joinColumns = @JoinColumn(name = "party_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Long getCurrentWorldId() {
        return currentWorldId;
    }

    public void setCurrentWorldId(Long currentWorldId) {
        this.currentWorldId = currentWorldId;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
    
    // Helper methods
    public void addMember(User user) {
        this.members.add(user);
    }
    
    public void removeMember(User user) {
        this.members.remove(user);
    }
}