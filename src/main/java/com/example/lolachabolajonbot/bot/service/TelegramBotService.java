package com.example.lolachabolajonbot.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodSerializable;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class TelegramBotService {

    public SendMessage sendTextMessage(long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    public void sendMessageGroup(long chatId, String photoUrl, String name, String defaultMessage) throws FileNotFoundException {
        File file = ResourceUtils.getFile(photoUrl);
        InputFile photo = new InputFile(file);
        SendPhoto message = new SendPhoto();
        SendVideo sendVideo = new SendVideo();
        message.setPhoto(photo);
        message.setChatId(chatId);
        message.setCaption(String.format(defaultMessage, name));

    }

    public SendAnimation sendAnimation(long chatId, String caption, InputFile animation, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendAnimation.builder()
                .chatId(chatId)
                .caption(caption)
                .parseMode("html")
                .animation(animation)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    //    public EditMessageMedia updateMessage(long chatId, long messageId, InputMediaAnimation animation, InlineKeyboardMarkup inlineKeyboardMarkup) {
//        EditMessageMedia media = new EditMessageMedia();
//        media.setMessageId((int) messageId);
//        media.setChatId(chatId);
//        media.setMedia(animation);
//        media.setReplyMarkup(inlineKeyboardMarkup);
//
//    }
    public DeleteMessage deleteMessage(Integer messageId, long chatId) {
        return DeleteMessage.builder()
                .messageId(messageId)
                .chatId(chatId)
                .build();
    }
}
