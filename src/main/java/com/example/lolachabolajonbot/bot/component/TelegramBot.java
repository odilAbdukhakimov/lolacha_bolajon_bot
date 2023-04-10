package com.example.lolachabolajonbot.bot.component;

import com.example.lolachabolajonbot.bot.component.message.DefaultConstants;
import com.example.lolachabolajonbot.bot.component.message.DefaultMessage;
import com.example.lolachabolajonbot.bot.config.BotConfig;
import com.example.lolachabolajonbot.bot.service.FileService;
import com.example.lolachabolajonbot.bot.service.ReplyKeyBoardService;
import com.example.lolachabolajonbot.bot.service.TelegramBotService;
import com.example.lolachabolajonbot.entity.GroupEntity;
import com.example.lolachabolajonbot.entity.GroupMemberEntity;
import com.example.lolachabolajonbot.entity.HolidayEntity;
import com.example.lolachabolajonbot.service.HolidayService;
import com.example.lolachabolajonbot.service.MemberService;
import com.example.lolachabolajonbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private static Long groupId = null;
    private static boolean isSendFile;
    private static boolean isMember;
    private static String fileUrl;
    private static String memberName;
    String dateBirth;
    private final ReplyKeyBoardService inlineKeyboard;
    private final FileService fileService;
    private final BotConfig config;
    private final UserService userService;
    private final MemberService memberService;
    private final TelegramBotService tgBotService;
    private final HolidayService holidayService;

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
                            fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL), inlineKeyboard.inviteGroupMarkup());
                    toExecute(send);
                } else if (text.equals("/home")) {
                    SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HOME,
                            fileService.getFile(DefaultConstants.HOME_URL), inlineKeyboard.userFrontMarkup());
                    groupId = null;
                    fileUrl = null;
                    isSendFile = false;
                    toExecute(send);
                } else if (text.equals("Guruhga qo'shish")) {
                    InputFile file = fileService.getFile(DefaultConstants.SHARE_GROUP_ANIMATION_URL);
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SHARE_TO_GROUP, file, inlineKeyboard.inviteGroupMarkup());
                    toExecute(sendAnimation);

                } else if (text.equals("Tug'ilgan kunini kiritish")) {
                    InputFile file = fileService.getFile(DefaultConstants.SEND_PHOTO_URL);
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.CHOOSE_OWN_GROUP, file, inlineKeyboard.groupList(chatId, false));
                    toExecute(sendAnimation);
                } else if (text.equals("Tabriklanuvchilar ro'yxati")) {
                    InputFile file = fileService.getFile(DefaultConstants.DELETE_MEMBER_URL);
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.CHOOSE_OWN_GROUP, file, inlineKeyboard.groupList(chatId, true));
                    toExecute(sendAnimation);
                } else if (text.equals("Biz haqimizda")) {
                    InputFile file = fileService.getFile(DefaultConstants.HOME_URL);
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.ABOUT_US, file, null);
                    toExecute(sendAnimation);
                } else if (text.length() > 14 && isSendFile) {
                    userService.addDefaultValues(groupId, text, fileUrl);
                    groupId = null;
                    fileUrl = null;
                    isSendFile = false;
                    SendAnimation sendMessage = tgBotService.sendAnimation(chatId, DefaultMessage.SUCCESS_SAVE_VALUES_TO_GROUP,
                            fileService.getFile(DefaultConstants.SUCCES_ANIMATION_URL), null);
                    SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.HOME,
                            fileService.getFile(DefaultConstants.HOME_URL), inlineKeyboard.userFrontMarkup());
                    toExecute(sendMessage);
                    toExecute(send);
                } else if (text.toLowerCase().startsWith("ism")) {
                    String[] split = text.split(":");
                    memberName = split[1];
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SEND_DATE,
                            fileService.getFile(DefaultConstants.ENTER_DATA_URL), inlineKeyboard.dateButtons(1950, 2023, 5, "year"));
                    toExecute(sendAnimation);
                } else {
                    InputFile inputFile = fileService.getFile(DefaultConstants.ERROR_ANIMATION_URL);
                    SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.NOT_FOUND_COMMAND, inputFile, null);
                    toExecute(sendAnimation);
                }

            } else if (message.hasPhoto() && !isSendFile) {
                List<PhotoSize> photo = update.getMessage().getPhoto();
                PhotoSize photoSize = photo.get(photo.size() - 1);
                fileUrl = saveFile(photoSize.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile(DefaultConstants.SEND_TEXT), null);
                if (isMember) {
                    sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SEND_NAME, fileService.getFile(DefaultConstants.ENTER_DATA_URL), null);
                    isMember = false;
                }
                toExecute(sendAnimation);
            } else if (message.hasVideo() && !isSendFile) {
                Video video = update.getMessage().getVideo();
                fileUrl = saveFile(video.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile(DefaultConstants.SEND_TEXT), null);
                if (isMember) {
                    sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SEND_NAME, fileService.getFile(DefaultConstants.ENTER_DATA_URL), null);
                    isMember = false;
                }
                toExecute(sendAnimation);
            } else if (message.hasAnimation() && !isSendFile) {
                Animation animation = update.getMessage().getAnimation();
                fileUrl = saveFile(animation.getFileId());
                isSendFile = true;
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.DEFAULT_SEND_SALUTATION, fileService.getFile(DefaultConstants.SEND_TEXT), null);
                if (isMember) {
                    sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SEND_NAME, fileService.getFile(DefaultConstants.ENTER_DATA_URL), null);
                    isMember = false;
                }
                toExecute(sendAnimation);
            }

        } else if (update.hasMyChatMember() && update.getMyChatMember().
                getNewChatMember().
                getStatus().
                equals("member")) {
            Long groupChatId = update.getMyChatMember().getChat().getId();
            Long userChatId = update.getMyChatMember().getFrom().getId();
            String groupName = update.getMyChatMember().getChat().getTitle();
            if (groupId == null) {
                userService.addGroup(userChatId, groupChatId, groupName);
                groupId = groupChatId;
                SendAnimation sendAnimation = tgBotService.sendAnimation(userChatId, DefaultMessage.DEFAULT_SEND_PHOTO, fileService.getFile(DefaultConstants.SEND_FILE_URL), null);
                toExecute(sendAnimation);
            }


            // send message
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Message message = update.getCallbackQuery().getMessage();
            Integer messageId = message.getMessageId();
            Long chatId = message.getChatId();
            if (data.startsWith("add_group:")) {
                String[] split = data.split(":");
                groupId = Long.valueOf(split[1]);
                isMember = true;
                InputFile file = fileService.getFile(DefaultConstants.SEND_PHOTO_URL);
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.OWN_PHOTO, file, inlineKeyboard.skipMarkup());
                toExecute(sendAnimation);
            } else if (data.equals("skip")) {
                GroupEntity byChatIdGroup = userService.getByChatIdGroup(groupId);
                fileUrl = byChatIdGroup.getDefaultPhotoUrl();
                InputFile file = fileService.getFile(DefaultConstants.ENTER_DATA_URL);
                SendAnimation sendAnimation = tgBotService.sendAnimation(chatId, DefaultMessage.SEND_NAME, file, null);
                toExecute(sendAnimation);
            } else if (data.startsWith("show")) {
                String[] split = data.split(":");
                groupId = Long.valueOf(split[1]);
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.memberList(String.valueOf(groupId)));
                toExecute(editMessageReplyMarkup);
            } else if (data.equals("//back")) {
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.memberList(String.valueOf(groupId)));
                toExecute(editMessageReplyMarkup);
            } else if (data.equals("/back")) {
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.groupList(chatId, true));
                toExecute(editMessageReplyMarkup);
            } else if (data.startsWith("memberId")) {
                String[] split = data.split(":");
                Integer memberId = Integer.valueOf(split[1]);
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.deleteMember(memberId));
                toExecute(editMessageReplyMarkup);
            } else if (data.startsWith("del")) {
                int memberId = Integer.parseInt(data.split(":")[1]);
                memberService.deleteMember(memberId);
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.memberList(String.valueOf(groupId)));
                toExecute(editMessageReplyMarkup);
            } else if (data.startsWith("year")) {
                String year = data.split(":")[1] + "-";
                dateBirth = year;
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.monthButtons());
                toExecute(editMessageReplyMarkup);
            } else if (data.startsWith("month")) {
                String month = data.split(":")[2] + "-";
                dateBirth += month;
                int endDateOfMonth = Integer.parseInt(data.split(":")[3]);
                EditMessageReplyMarkup editMessageReplyMarkup = tgBotService.updateMessage(messageId, chatId, inlineKeyboard.dateButtons(1, endDateOfMonth, 7, "date"));
                toExecute(editMessageReplyMarkup);
            } else if (data.startsWith("date")) {
                String day = data.split(":")[1];
                dateBirth += day;
                memberService.addMember(groupId, memberName, fileUrl, dateBirth);
                memberName = null;
                dateBirth = null;
                SendAnimation send = tgBotService.sendAnimation(chatId, DefaultMessage.SUCCESS_SAVE_VALUES_TO_GROUP,
                        fileService.getFile(DefaultConstants.SUCCES_ANIMATION_URL), null);
                toExecute(send);
                SendAnimation home = tgBotService.sendAnimation(chatId, DefaultMessage.HOME,
                        fileService.getFile(DefaultConstants.HOME_URL), inlineKeyboard.userFrontMarkup());
                toExecute(home);
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

    private void toExecute(Object message) {
        try {
            if (message instanceof SendMessage) {
                execute((SendMessage) message);
            } else if (message instanceof SendPhoto) {
                execute((SendPhoto) message);
            } else if (message instanceof SendVideo) {
                execute((SendVideo) message);
            } else if (message instanceof SendAnimation) {
                execute((SendAnimation) message);
            } else if (message instanceof EditMessageReplyMarkup) {
                execute((EditMessageReplyMarkup) message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 30 6 * * *")
    public void sendMessageBirth() {
        for (GroupMemberEntity member : memberService.getByDate()) {
            SendMediaBotMethod<Message> messageSendMediaBotMethod = tgBotService.sendMessageGroup(member.getGroupEntity().getChatId(), member.getPhotoUrl(),
                    member.getFullName(), member.getGroupEntity().getDefaultMessage());
            toExecute(messageSendMediaBotMethod);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 9 * * *")
    public void sendMessageHoliday() {
        HolidayEntity holidayInfo = holidayService.getHolidayInfo();
        List<Long> allGroupChatIdList = holidayService.getAllGroupChatIdList();
        allGroupChatIdList.forEach((id) ->
                {
                    try {
                        toExecute(tgBotService.sendMessageGroup(id, holidayInfo.getPhotoUrl(),
                                holidayInfo.getName(), holidayInfo.getText()));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

        );

    }
}
