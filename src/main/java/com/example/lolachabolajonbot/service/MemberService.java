package com.example.lolachabolajonbot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.GroupRepository;
import com.example.lolachabolajonbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository repository;
    private final GroupRepository groupRepository;

    public UserEntity getByChatId(long chatId) {
        return repository.findByChatId(chatId).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public void addMember(long chatId, int groupId, String fullName, String photoUrl, LocalDateTime birthDate) {
        UserEntity byId = getByChatId(chatId);
        GroupMemberEntity build = GroupMemberEntity.builder()
                .addedDate(LocalDateTime.now())
                .birthDate(birthDate)
                .photoUrl(photoUrl)
                .fullName(fullName)
                .build();
        List<GroupEntity> groupList = byId.getGroupList();
        for (GroupEntity group : groupList) {
            if (group.getId() == groupId) {
                List<GroupMemberEntity> members = group.getMembers();
                if (photoUrl != null)
                    build.setPhotoUrl(group.getDefaultPhotoUrl());
                members.add(build);
                group.setMembers(members);
                break;
            }
        }
        byId.setGroupList(groupList);
        repository.save(byId);
    }

    public void deleteMember(long chatId, GroupMemberEntity member, int groupId) {
        UserEntity byId = getByChatId(chatId);
        for (GroupEntity groupEntity : byId.getGroupList()) {
            if (groupId == groupEntity.getId()) {
                List<GroupMemberEntity> members = groupEntity.getMembers();
                members.remove(member);
                groupEntity.setMembers(members);
                break;
            }
        }
        repository.save(byId);
    }

    public GroupMemberEntity getById(int groupId, int memberId) {
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        for (GroupMemberEntity member : groupEntity.getMembers()) {
            if (member.getId() == memberId)
                return member;
        }
        throw new RuntimeException("Member not found");
    }
}
