package com.example.lolachabolajonbot.bot.component;

import com.example.lolachabolajonbot.bot.component.message.DefaultConstants;
import com.example.lolachabolajonbot.bot.component.message.DefaultMessage;
import com.example.lolachabolajonbot.bot.config.BotConfig;
import com.example.lolachabolajonbot.bot.service.FileService;
import com.example.lolachabolajonbot.bot.service.ReplyKeyBoardService;
import com.example.lolachabolajonbot.bot.service.TelegramBotService;
import com.example.lolachabolajonbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;

import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final ReplyKeyBoardService inlineKeyboard;
    private static Long groupId = null;
    private static boolean isSendFile;
    private static Integer messageId;
    private static String fileUrl;
    private final FileService fileService;
    private final BotConfig config;
    private final UserService userService;
    private final TelegramBotService tgBotService;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getChat().getType().equals("private")) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            if (message.hasText()) {
                // text uchun
                String text = message.getText();
                if (text.equals("/start")) {
                    userService.createUser(chatId, message.getChat().getFirstName() + " "
                            + message.getChat().getLastName());
                    SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HI,
                            fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL), inlineKeyboard.userFrontMarkup());
                    //SendMessage send = tgBotService.sendTextMessage(chatId, DefaultMessage.HI, inlineKeyboard.userFrontMarkup());
                    toExecute(send);
                } else if (text.equals("/home")) {
                    SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HI,
                            fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL), inlineKeyboard.userFrontMarkup());
                    groupId = null;
                    fileUrl = null;
                    isSendFile = false;
                    toExecute(send);
                } else if (text.length() > 14 && isSendFile) {
                    userService.addDefaultValues(groupId, text, fileUrl);
                    groupId = null;
                    fileUrl = null;
                    isSendFile = false;
                    SendAnimation sendMessage = tgBotService.sendAnimation(chatId, DefaultMessage.SUCCESS_SAVE_VALUES_TO_GROUP,
                            fileService.getFile(DefaultConstants.SUCCES_ANIMATION_URL), null);
                    SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HI,
                            fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL), inlineKeyboard.userFrontMarkup());
                    toExecute(sendMessage);
                    toExecute(send);
                }

            } else if (message.hasPhoto() && !isSendFile) {
                List<PhotoSize> photo = update.getMessage().getPhoto();
                PhotoSize photoSize = photo.get(photo.size() - 1);
                fileUrl = saveFile(photoSize.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile("disc/share_group.gif"), null);
                try {
                    execute(sendAnimation);
                } catch (Exception e) {

                }
            } else if (message.hasVideo() && !isSendFile) {
                Video video = update.getMessage().getVideo();
                fileUrl = saveFile(video.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile("disc/share_group.gif"), null);
                try {
                    execute(sendAnimation);
                } catch (Exception e) {

                }
            } else if (message.hasAnimation() && !isSendFile) {
                Animation animation = update.getMessage().getAnimation();
                fileUrl = saveFile(animation.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile("disc/share_group.gif"), null);
                try {
                    execute(sendAnimation);
                } catch (Exception e) {

                }
            }

        } else if (update.hasMyChatMember() && update.getMyChatMember().getNewChatMember().getStatus().equals("member")) {
            Long groupChatId = update.getMyChatMember().getChat().getId();
            Long userChatId = update.getMyChatMember().getFrom().getId();
            messageId = update.getMessage().getMessageId();
            String groupName = update.getMyChatMember().getChat().getTitle();
            if (groupId == null) {
                userService.addGroup(userChatId, groupChatId, groupName);
                groupId = groupChatId;
                SendAnimation sendAnimation = tgBotService.sendAnimation(userChatId, DefaultMessage.DEFAULT_SEND_PHOTO, fileService.getFile("disc/share_group.gif"), null);
                try {
                    execute(sendAnimation);
                } catch (Exception e) {

                }
            }


            // send message
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            Long chatId = message.getChatId();
            if (data.equals("share_group")) {
                InputFile file = fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL);
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SHARE_TO_GROUP, file, inlineKeyboard.inviteGroupMarkup());
                toExecute(sendAnimation);
                try {
                    DeleteMessage deleteMessage = tgBotService.deleteMessage(messageId, chatId);
                    execute(sendAnimation).getMessageId();
                    execute(deleteMessage);
                } catch (Exception e) {
                }
            } else if (data.equals("/back")) {
                tgBotService.deleteMessage(messageId, chatId);
                SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HI,
                        fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL), inlineKeyboard.userFrontMarkup());
                groupId = null;
                fileUrl = null;
                isSendFile = false;
                toExecute(send);
            }

        }


    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private String saveFile(String id) {
        try {
            GetFile getFile = new GetFile(id);
            String fileUrl = execute(getFile).getFileUrl(getBotToken());
            String filePath = execute(getFile).getFilePath();
            fileService.saveFile(filePath, new URL(fileUrl));
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer toExecute(SendAnimation message) {
        try {
           return execute(message).getMessageId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
