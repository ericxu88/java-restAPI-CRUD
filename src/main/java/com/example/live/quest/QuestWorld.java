package com.example.live.quest;

import jakarta.persistence.*;

@Entity
@Table(name = "quest_worlds")
public class QuestWorld {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "level_req")
    private Integer levelRequirement = 1;
    
    @Column(name = "tiles_json", columnDefinition = "TEXT")
    private String tilesJson; // JSON representation of the map
    
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

    public Integer getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(Integer levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    public String getTilesJson() {
        return tilesJson;
    }

    public void setTilesJson(String tilesJson) {
        this.tilesJson = tilesJson;
    }
}