package com.example.lolachabolajonbot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.GroupRepository;
import com.example.lolachabolajonbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final UserRepository repository;
    private final GroupRepository groupRepository;

    public UserEntity getByChatId(long chatId) {
        return repository.findByChatId(chatId).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public void addMember(long chatId, long groupId, String fullName, String photoUrl, LocalDate birthDate) {
        // UserEntity byId = getByChatId(chatId);
        Optional<GroupEntity> byChatId = groupRepository.findByChatId(groupId);
        if (byChatId.isPresent()) {
            GroupMemberEntity build = GroupMemberEntity.builder()
                    .addedDate(LocalDate.now())
                    .birthDate(birthDate)
                    .photoUrl(photoUrl)
                    .fullName(fullName)
                    .build();
            GroupEntity groupEntity = byChatId.get();
            List<GroupMemberEntity> members = groupEntity.getMembers();
            if (members == null) {
                members = List.of(build);
            }
            members.add(build);
            groupEntity.setMembers(members);
            groupRepository.save(groupEntity);
        }

//        List<GroupEntity> groupList = byId.getGroupList();
//        for (GroupEntity group : groupList) {
//            if (group.getId() == groupId) {
//                List<GroupMemberEntity> members = group.getMembers();
//                if (photoUrl != null)
//                    build.setPhotoUrl(group.getDefaultPhotoUrl());
//                members.add(build);
//                group.setMembers(members);
//                break;
//            }
//        }
//        byId.setGroupList(groupList);
//        repository.save(byId);
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
