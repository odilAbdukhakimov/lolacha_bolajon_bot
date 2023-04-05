package com.example.lolachabolajonbot.bot.service;

import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReplyKeyBoardService {
    private final UserRepository userRepository;

    public ReplyKeyboardMarkup userFrontMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow1 = new KeyboardRow(List.of(new KeyboardButton("Guruhga qo'shish")));
        KeyboardRow keyboardRow2 = new KeyboardRow(List.of(new KeyboardButton("Tug'ilgan kunini kiritish"),
                new KeyboardButton("Tabriklanuvchilar ro'yxati")));
        KeyboardRow keyboardRow3 = new KeyboardRow(List.of(new KeyboardButton("Biz haqimizda")));
        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow1, keyboardRow2, keyboardRow3));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup groupList(long chatId) {
        int count = 0;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        Optional<UserEntity> byChatId = userRepository.findByChatId(chatId);
        if (byChatId.isEmpty()) {
            return null;
        }
        ArrayList<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        List<GroupEntity> groupList = byChatId.get().getGroupList();
        for (GroupEntity group : groupList) {
            if (count == 3) {
                lists.add(row);
                row = new ArrayList<>();
                count = 0;
            }
            button.setText(group.getGroupName());
            button.setCallbackData("add_group:" + group.getChatId());
            row.add(button);
            count++;
        }
        if (groupList.size() % 3 == 2) {
            InlineKeyboardButton button1 = new InlineKeyboardButton();
            InlineKeyboardButton button2 = new InlineKeyboardButton();
            button1.setText(groupList.get(groupList.size() - 2).getGroupName());
            button1.setCallbackData("add_group:" + groupList.get(groupList.size() - 2).getChatId());
            button2.setText(groupList.get(groupList.size() - 1).getGroupName());
            button2.setCallbackData("add_group:" + groupList.get(groupList.size() - 1).getChatId());
            lists.add(List.of(button1, button2));
        } else if (groupList.size() % 3 == 1) {
            InlineKeyboardButton button3 = new InlineKeyboardButton();
            button3.setText(groupList.get(groupList.size() - 1).getGroupName());
            button3.setCallbackData("add_group:" + groupList.get(groupList.size() - 1).getChatId());
            lists.add(List.of(button3));
        }
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inviteGroupMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setUrl("https://t.me/Lolacha_bolajon_bot?startgroup=true");
        button1.setText("guruhga o'rnatish");
        List<InlineKeyboardButton> row = List.of(button1);
        inlineKeyboardMarkup.setKeyboard(List.of(row));
        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup skipMarkup(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setCallbackData("skip");
        button1.setText("Rasm yubormayman");
        List<InlineKeyboardButton> row = List.of(button1);
        inlineKeyboardMarkup.setKeyboard(List.of(row));
        return inlineKeyboardMarkup;
    }
}
