package com.example.lolachabolajonbot.bot.component.message;

public interface DefaultMessage {
    String HI = "Assalomu alaykum";
    String SHARE_TO_GROUP = "Guruhlarga qo'shing va foydalanuvchilarga tug'ilgan kun tabriklarini kiritishni boshlang";
    String DEFAULT_SEND_PHOTO = "Tug'ilgan kun tabrikida standart rasmni jo'nating \n(! Agar guruh azosini rasmi yuborilmasa ushbu rasm yuboriladi)";
    String DEFAULT_SEND_SALUTATION = "Tug'ilgan kun tabriki standart matnini jo'nating \n(! Matn " +
            "orasida'{}' ushbu qavslarni qoldirsangiz, tabriklanuvchi guruh azosini ismini joylashtirb beramiz." +
            "\n Masalan: Assalomu alaykum {}. -> Assalomu alaykum <b> Odil </b>.) ";
    String SUCCESS_SAVE_VALUES_TO_GROUP = "Ma'lumotlar muvaffaqiyatli saqlandi!";
    String CHOOSE_OWN_GROUP = "Tabriklanuvchi qaysi guruhda ? ";
    String OWN_PHOTO = "Foydalanuvchi rasmini jo'nating. Agar rasm jo'natishni xohlamasangiz, \'Rasm yubormayman\' bosing ";
    String SEND_NAME = "Foydalanuvchi ismini kiriting- \'Ism: ...\' . Masalan: Ism:Odil";
    String SEND_DATE = "Foydalanuvchi tug'ilgan kunini (Kun:kun-oy-yil) formatda kiriting- \'Kun: ...\' . Masalan: Kun:09-01-1999";
    String NOT_NAME = "Ism jo'natiwda xatolik bo'ldi, \'Ism: ... \' quyida formatda to'ldiring";
}
