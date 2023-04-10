package com.example.lolachabolajonbot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.HolidayEntity;
import com.example.lolachabolajonbot.repository.GroupRepository;
import com.example.lolachabolajonbot.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final HolidayRepository repository;
    private final GroupRepository groupRepository;

    public HolidayEntity getHolidayInfo() {
        for (HolidayEntity holidayEntity : repository.findAll()) {
            if (holidayEntity.getDate().equals(LocalDate.now().toString().substring(5)))
                return holidayEntity;
        }
        return null;
    }

    public List<Long> getAllGroupChatIdList() {
        List<Long> res = new ArrayList<>();
        for (GroupEntity groupEntity : groupRepository.findAll()) {
            res.add(groupEntity.getChatId());
        }
        return res;
    }
}
