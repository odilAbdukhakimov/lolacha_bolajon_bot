package com.example.lolachabolajonbot.bot.component.message;

public interface DefaultMessage {
    String HI = "Assalomu alaykum.\n Bot ishlashga tayyor. Avvaliga guruhga botni qo'shib olishingiz kerak. " +
            "Guruh a'zolarini tug'ilgan kunlarini kiritganizdan keyin,\n bot avtomat holatda guruh a'zolarini " +
            "tug'ilgan kun bilan tabriklab boshlaydi, unda boshladik \uD83E\uDD17";
    String HOME = "Bot o'z ish faoliyatida, agar kamchilik yoki xatoliklar kuzatilsa yangilangan versiyasida\n bartaraf" +
            " etib boriladi. O'zingizga kerakli xizmatlarni tanlashingiz mumkin, marhamat";
    String SHARE_TO_GROUP = "Guruhlarga qo'shing va foydalanuvchilarga tug'ilgan kun tabriklarini kiritishni boshlang";
    String DEFAULT_SEND_PHOTO = "Tug'ilgan kun tabrikida standart rasmni jo'nating \n(! Agar guruh azosini rasmi yuborilmasa ushbu rasm yuboriladi).\n " +
            "Bot kichik video(10mb gacha) yoki animatsiya(.gif) larni ham qabul qila oladi. Ushbu fayllar standart rasm o'rnida ishlatish mumkin";
    String DEFAULT_SEND_SALUTATION = "Tug'ilgan kun tabriki standart matnini jo'nating \n(! Matn " +
            "orasida'{}' ushbu qavslarni qoldirsangiz, tabriklanuvchi guruh azosini ismini joylashtirb beramiz." +
            "\n Masalan: Assalomu alaykum {}. -> Assalomu alaykum <b> Odil </b>.) ";
    String SUCCESS_SAVE_VALUES_TO_GROUP = "Ma'lumotlar muvaffaqiyatli saqlandi!";
    String CHOOSE_OWN_GROUP = "Foydalanuvchi qaysi guruhda ? ";
    String OWN_PHOTO = "Foydalanuvchi rasmini(video yoki animatsiya) jo'nating. Agar rasm jo'natishni xohlamasangiz, \'Rasm yubormayman\' bosing ";
    String SEND_NAME = "Foydalanuvchi ismini kiriting- \'Ism: ...\' .\n Masalan: <b>Ism:</b>Odil";
    String SEND_DATE = "Foydalanuvchi tug'ilgan kunini <b>(Kun:kun-oy-yil)</b> formatda kiriting- \'Kun: ...\' .\n Masalan: <b>Kun:</b>09-01-1999";
    String ABOUT_US = "Bot tabrik jo'natish uchun mo'ljallangan. Ushbu botdan tashqari xohlagan turkumdagi <b>Internet saytlar</b> va <b>Telegram bot</b> yaratib beramiz." +
            "\n \uD83D\uDCDE : <u>+998 99 665 9998</u> "+
            "\n \uD83C\uDF10 : https://oabdukhakimov.uz ";
    String NOT_FOUND_COMMAND = "Xato kiritingiz, iltimos to'ri formatda kiriting (na'munaga qarang)";
}
