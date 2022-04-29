package com.rom.english.learnbot.bot.handler;

import com.rom.english.protobuf.CallbackProto;

public interface CallbackQueryDataWrapper {

    String encode(CallbackProto.Payload data);

    CallbackProto.Payload decode(String data);

}