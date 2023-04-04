package com.example.lolachabolajonbot.repository;

import com.example.lolachabolajonbot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByChatId(long chatId);
}
