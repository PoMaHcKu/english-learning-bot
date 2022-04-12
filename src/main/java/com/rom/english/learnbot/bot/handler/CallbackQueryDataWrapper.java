package com.rom.english.learnbot.bot.handler;

import com.rom.english.protobuf.CallbackProto;

public interface CallbackQueryDataWrapper {

    String encode(CallbackProto.Callback data);

    CallbackProto.Callback decode(String data);

}