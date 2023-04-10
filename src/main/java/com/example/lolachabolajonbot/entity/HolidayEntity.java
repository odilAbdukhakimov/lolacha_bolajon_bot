package com.example.lolachabolajonbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayEntity {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String date;
    @Column(length = 1000)
    private String text;
    private String photoUrl;
}
