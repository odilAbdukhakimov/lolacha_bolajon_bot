package com.example.lolachabolajonbot.bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class ReplyKeyBoardService {
    public InlineKeyboardMarkup userFrontMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setCallbackData("share_group");
        button1.setText("Guruhga qo'shish");
        List<InlineKeyboardButton> row1 = List.of(button1);

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setCallbackData("add_member");
        button2.setText("Tug'ilgan kunini kiritish");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setCallbackData("members_list");
        button3.setText("Tabriklanuvchilar ro'yxati");
        List<InlineKeyboardButton> row2 = List.of(button2, button3);

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setCallbackData("about_us");
        button4.setText("Biz haqimizda");
        List<InlineKeyboardButton> row4 = List.of(button4);
        inlineKeyboardMarkup.setKeyboard(List.of(row1, row2, row4));
        return inlineKeyboardMarkup;
    }
//    public ReplyKeyboardMarkup userFrontMarkup1(){
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup;
//        replyKeyboardMarkup.se
//        ReplyKeyboard
//    }

    private List<InlineKeyboardButton> back() {
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setCallbackData("/back");
        button1.setText("<| ortga");
        List<InlineKeyboardButton> row = List.of(button1);
        return row;
    }

    public InlineKeyboardMarkup inviteGroupMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setUrl("https://t.me/Lolacha_bolajon_bot?startgroup=true");
        button1.setText("guruhga o'rnatish");
        List<InlineKeyboardButton> row = List.of(button1);
        inlineKeyboardMarkup.setKeyboard(List.of(row, back()));
        return inlineKeyboardMarkup;
    }
}
