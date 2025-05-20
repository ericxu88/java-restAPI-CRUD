package com.example.live.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
    
    // New fields
    @Column(name = "avatar_url")
    private String avatarUrl;
    
    @Column(name = "timezone")
    private String timezone;
    
    @Column(name = "xp")
    private Integer xp = 0;
    
    @Column(name = "gold")
    private Integer gold = 0;
    
    @Column(name = "password_hash")
    private String passwordHash;

    // Existing getters and setters
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    // New getters and setters
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}