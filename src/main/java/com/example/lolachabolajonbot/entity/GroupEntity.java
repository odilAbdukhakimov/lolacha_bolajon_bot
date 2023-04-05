package com.example.lolachabolajonbot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupEntity {
    @Id
    @GeneratedValue
    private int id;
    private String groupName;
    @Column(unique = true)
    private long chatId;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<GroupMemberEntity> members;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Column(length = 1000)
    private String defaultMessage;
    private String defaultPhotoUrl;

}
