package com.example.lolachabolajonbot.repository;

import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<GroupMemberEntity,Integer> {
    List<GroupMemberEntity> getByGroupEntityChatId(long groupEntity_chatId);
}
