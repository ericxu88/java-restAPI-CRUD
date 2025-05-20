package com.example.live.reward;

import com.example.live.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "rewards")
public class Reward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardType type;
    
    @Column(name = "amount")
    private Integer amount;
    
    @Column(name = "rarity")
    @Enumerated(EnumType.STRING)
    private Rarity rarity = Rarity.COMMON;
    
    @Column(name = "item_code")
    private String itemCode; // For cosmetic/item rewards
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Who owns this reward
    
    // Enum for reward types
    public enum RewardType {
        XP, GOLD, ITEM
    }
    
    // Enum for rarity levels
    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RewardType getType() {
        return type;
    }

    public void setType(RewardType type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}