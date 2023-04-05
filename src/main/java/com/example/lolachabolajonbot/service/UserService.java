package com.example.lolachabolajonbot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.GroupRepository;
import com.example.lolachabolajonbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public UserEntity getByChatId(long chatId) {
        return userRepository.findByChatId(chatId).orElseThrow(() -> new RuntimeException("user not found"));
    }
    public GroupEntity getByChatIdGroup(long chatId){
        return groupRepository.findByChatId(chatId).orElseThrow(() -> new RuntimeException("group not found"));
    }

    public void createUser(long chatId, String fullName) {
        UserEntity userEntity = userRepository.findByChatId(chatId).orElse(null);
        if (userEntity != null)
            throw new RuntimeException("User already exist");
        UserEntity build = UserEntity.builder()
                .chatId(chatId)
                .fullName(fullName)
                .build();
        userRepository.save(build);
    }

    public void addGroup(long userChatId, long groupChatId, String groupName) {
        Optional<GroupEntity> optionalGroup = groupRepository.findByChatId(groupChatId);
        UserEntity byChatId = getByChatId(userChatId);
        if (optionalGroup.isEmpty()) {
            GroupEntity build = GroupEntity.builder()
                    .chatId(groupChatId)
                    .groupName(groupName)
                    .userEntity(byChatId)
                    .build();
            groupRepository.save(build);
        }
    }

    public void addDefaultValues(long groupChatId, String defaultMessage, String defaultPhotoUrl) {
        Optional<GroupEntity> byChatId = groupRepository.findByChatId(groupChatId);
        if (byChatId.isEmpty()) return;
        GroupEntity groupEntity = byChatId.get();
        groupEntity.setDefaultMessage(defaultMessage);
        groupEntity.setDefaultPhotoUrl(defaultPhotoUrl);
        groupRepository.save(groupEntity);
    }

//    private void setGroupEntity(long userChatId, GroupEntity groupEntity) {
//        UserEntity user = getByChatId(userChatId);
//        List<GroupEntity> groupList = user.getGroupList();
//        if (groupList == null)
//            user.setGroupList(List.of(groupEntity));
//        else {
//            List<GroupEntity> groupList1 = user.getGroupList();
//            groupList1.add(groupEntity);
//            user.setGroupList(groupList1);
//        }
//    }
}
