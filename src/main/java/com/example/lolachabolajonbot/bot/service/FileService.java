package com.example.lolachabolajonbot.bot.service;

import com.example.lolachabolajonbot.bot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaAnimation;

import java.io.File;
import java.net.URL;

import static org.springframework.util.ResourceUtils.getFile;

@Service
@RequiredArgsConstructor
public class FileService {
    private static final String PATH_PATH = "src/main/resources/static/";

    public void saveFile(String fileName, URL url) {
        try {
            File photo = new File(PATH_PATH + fileName);
            FileUtils.copyURLToFile(url, photo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputFile getFile(String fileName) {
        try {
            File file = ResourceUtils.getFile(PATH_PATH + fileName);
            return new InputFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputMedia getMediaAnimation(String fileName) {
        return new InputMediaAnimation(PATH_PATH + fileName);
    }
}
