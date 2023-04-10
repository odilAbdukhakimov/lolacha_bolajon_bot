package com.example.lolachabolajonbot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.GroupRepository;
import com.example.lolachabolajonbot.repository.MemberRepository;
import com.example.lolachabolajonbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public void addMember(long groupId, String fullName, String photoUrl, String date) {
        LocalDate dateTime = LocalDate.parse(date);
        GroupMemberEntity build = GroupMemberEntity.builder()
                .addedDate(LocalDate.now())
                .birthDate(dateTime)
                .photoUrl(photoUrl)
                .fullName(fullName)
                .groupEntity(groupRepository.findByChatId(groupId).orElse(null))
                .build();
        memberRepository.save(build);
    }

    public void deleteMember(int memberId) {
        GroupMemberEntity byId = getById(memberId);
        memberRepository.delete(byId);
    }

    public GroupMemberEntity getById(int memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<GroupMemberEntity> getByDate() {
        List<GroupMemberEntity> all = memberRepository.findAll();
        List<GroupMemberEntity> res = new ArrayList<>();
        all.stream().parallel().forEach((member) ->
        {
            if (member.getBirthDate().toString().substring(4).equals(
                    LocalDate.now().toString().substring(4))) {
                res.add(member);
            }
        });
        return res;
    }
}
