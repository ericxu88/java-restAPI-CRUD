package com.example.live.quest;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bosses")
public class Boss {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "world_id", nullable = false)
    private QuestWorld world;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "hp", nullable = false)
    private Integer hp;
    
    @Column(name = "attack_power")
    private Integer attackPower;
    
    @Column(name = "unlock_date")
    private LocalDate unlockDate;
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestWorld getWorld() {
        return world;
    }

    public void setWorld(QuestWorld world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(Integer attackPower) {
        this.attackPower = attackPower;
    }

    public LocalDate getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(LocalDate unlockDate) {
        this.unlockDate = unlockDate;
    }
}