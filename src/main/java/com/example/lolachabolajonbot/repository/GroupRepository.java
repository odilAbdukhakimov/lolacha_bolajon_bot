package com.example.lolachabolajonbot.repository;

import com.example.lolachabolajonbot.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity,Integer> {
    Optional<GroupEntity>findByChatId(long chatId);
}
