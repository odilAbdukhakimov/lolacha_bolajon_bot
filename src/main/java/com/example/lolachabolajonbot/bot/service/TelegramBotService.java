package com.example.lolachabolajonbot.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodSerializable;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

@Service
@RequiredArgsConstructor
public class TelegramBotService {
    private final FileService fileService;

    public SendMessage sendTextMessage(long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    public SendMediaBotMethod<Message> sendMessageGroup(long chatId, String photoUrl, String name, String defaultMessage) throws FileNotFoundException {
        InputFile file = fileService.getFile(photoUrl);
        String text = defaultMessage.replace("{}","<b>" + name + "</b>");
        if (photoUrl.startsWith("photo")) {
            return SendPhoto.builder()
                    .chatId(chatId)
                    .caption(text)
                    .parseMode("html")
                    .photo(file)
                    .build();
        } else if (photoUrl.startsWith("video")) {
            return SendVideo.builder()
                    .chatId(chatId)
                    .caption(text)
                    .parseMode("html")
                    .video(file)
                    .build();
        } else if (photoUrl.startsWith("animation")) {
            return SendAnimation.builder()
                    .animation(file)
                    .chatId(chatId)
                    .parseMode("html")
                    .caption(text)
                    .build();
        }
        return null;
    }

    public SendAnimation sendAnimation(long chatId, String caption, InputFile animation, ReplyKeyboard inlineKeyboardMarkup) {
        return SendAnimation.builder()
                .chatId(chatId)
                .caption(caption)
                .parseMode("html")
                .animation(animation)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }

    public DeleteMessage deleteMessage(Integer messageId, long chatId) {
        return DeleteMessage.builder()
                .messageId(messageId)
                .chatId(chatId)
                .build();
    }
    public EditMessageReplyMarkup updateMessage(Integer messageId, long chatId,InlineKeyboardMarkup inlineKeyboardMarkup){
        return EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }
}
