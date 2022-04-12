package com.rom.english.learnbot.bot.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rom.english.protobuf.CallbackProto;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class CallbackQueryBase64DataWrapper implements CallbackQueryDataWrapper {

    private final Base64.Decoder decoder = Base64.getDecoder();
    private final Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public String encode(CallbackProto.Callback data) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            data.writeTo(out);
            return encoder.encodeToString(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Byte writing exception", e);
        }
    }

    @Override
    public CallbackProto.Callback decode(String data) {
        byte[] bytes = decoder.decode(data);
        try {
            return CallbackProto.Callback.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Byte reading exception", e);
        }
    }
}