package com.example.lolachabolajonbot.bot.service;

import com.example.lolachabolajonbot.bot.component.message.DefaultConstants;
import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import com.example.lolachabolajonbot.entity.UserEntity;
import com.example.lolachabolajonbot.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

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

    public InlineKeyboardMarkup groupList(long chatId, boolean show) {
        String typeGroup = "add_group:";
        if (show) {
            typeGroup = "show:";
        }
        int count = 0;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        Optional<UserEntity> byChatId = userRepository.findByChatId(chatId);
        if (byChatId.isEmpty()) {
            return null;
        }
        ArrayList<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<GroupEntity> groupList = byChatId.get().getGroupList();
        for (GroupEntity group : groupList) {
            if (count == 3) {
                lists.add(row);
                row = new ArrayList<>();
                count = 0;
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(group.getGroupName());
            button.setCallbackData(typeGroup + group.getChatId());
            row.add(button);
            count++;
        }
        lists.add(row);
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

    public InlineKeyboardMarkup skipMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setCallbackData("skip");
        button1.setText("Rasm yubormayman ⏭ ");
        List<InlineKeyboardButton> row = List.of(button1);
        inlineKeyboardMarkup.setKeyboard(List.of(row));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup memberList(String groupChatId) {
        List<GroupMemberEntity> memberList = memberRepository.getByGroupEntityChatId(Long.parseLong(groupChatId));
        int count = 0;
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        for (GroupMemberEntity group : memberList) {
            if (count == 3) {
                lists.add(row);
                row = new ArrayList<>();
                count = 0;
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(group.getFullName());
            button.setCallbackData("memberId:" + group.getId());
            row.add(button);
            count++;
        }
        lists.add(row);
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setCallbackData("/back");
        back.setText("ortga");
        lists.add(List.of(back));
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup deleteMember(Integer memberId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("o'chirish \uD83D\uDDD1 ");
        b1.setCallbackData("del:" + memberId);
        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("ortga");
        b2.setCallbackData("//back");
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(b1, b2)));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup dateButtons(int startDate, int endDate, int rows, String data) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        ArrayList<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        int count = 0;
        for (int i = startDate; i <= endDate; i++) {
            if (count == rows) {
                lists.add(row);
                row = new ArrayList<>();
                count = 0;
            }
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.valueOf(i));
            if (i < 10)
                button.setCallbackData(data + ":0" + i);
            else
                button.setCallbackData(data + ":" + i);
            row.add(button);
            count++;
        }
        lists.add(row);
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup monthButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        ArrayList<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(DefaultConstants.JANUARY.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.JANUARY);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.FEBRUARY.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.FEBRUARY);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.MARCH.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.MARCH);
        row.add(button);
        lists.add(row);
        //---------------------------------------------------------------------------
        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.APRIL.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.APRIL);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.MAY.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.MAY);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.JUNE.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.JUNE);
        row.add(button);
        lists.add(row);
        //---------------------------------------------------------------------------
        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.JULY.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.JULY);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.AUGUST.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.AUGUST);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.SEPTEMBER.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.SEPTEMBER);
        row.add(button);
        lists.add(row);
        //---------------------------------------------------------------------------
        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.OCTOBER.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.OCTOBER);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.NOVEMBER.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.NOVEMBER);
        row.add(button);
        button = new InlineKeyboardButton();
        button.setText(DefaultConstants.DECEMBER.split(":")[0]);
        button.setCallbackData("month:" + DefaultConstants.DECEMBER);
        row.add(button);
        lists.add(row);
        inlineKeyboardMarkup.setKeyboard(lists);
        return inlineKeyboardMarkup;
    }
}
