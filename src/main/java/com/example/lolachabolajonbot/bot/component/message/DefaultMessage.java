package com.example.lolachabolajonbot.bot.component.message;

public interface DefaultMessage {
    String HI = "Assalomu alaykum";
    String SHARE_TO_GROUP="Guruhlarga qo'shing va foydalanuvchilarga tug'ilgan kun tabriklarini kiritishni boshlang";
    String DEFAULT_SEND_PHOTO="Tug'ilgan kun tabrikida standart rasmni jo'nating \n(! Agar guruh azosini rasmi yuborilmasa ushbu rasm yuboriladi)";
    String DEFAULT_SEND_SALUTATION="Tug'ilgan kun tabriki standart matnini jo'nating \n(! Matn " +
            "orasida'{}' ushbu qavslarni qoldirsangiz, tabriklanuvchi guruh azosini ismini joylashtirb beramiz." +
            "\n Masalan: Assalomu alaykum {}. -> Assalomu alaykum <b> Odil </b>.) ";
    String SUCCESS_SAVE_VALUES_TO_GROUP="Ma'lumotlar muvaffaqiyatli saqlandi!";
}
