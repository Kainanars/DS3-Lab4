package com.sd3.market.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    public AbstractEntity(){
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        active = true;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq_gen")
    @SequenceGenerator(name = "entity_seq_gen", sequenceName = "entity_seq", allocationSize = 1)
    @Column(name = "IX_ID")
    private long id;
    @Column(name = "DT_CREATED_AT")
    private final LocalDateTime createdAt;
    @Column(name = "DT_UPDATED_AT")
    private LocalDateTime updatedAt;
    @Column(name = "ST_ACTIVE")
    private Boolean active;


    public long getId() {
        return id;
    }

    public void setId(long idPassed) {
        id = idPassed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAtPassed) {
        updatedAt = updatedAtPassed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean activePassed) {
        active = activePassed;
    }
}
