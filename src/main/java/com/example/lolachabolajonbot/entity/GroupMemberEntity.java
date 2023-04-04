package com.example.lolachabolajonbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMemberEntity {
    @Id
    @GeneratedValue
    private int id;
    private String fullName;
    private LocalDateTime birthDate;
    private String photoUrl;
    private LocalDateTime addedDate;
}
