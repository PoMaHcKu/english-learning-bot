package com.rom.english.parsers.service;

import com.rom.english.parsers.model.WordReference;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class DefaultWordReferenceUploader implements WordReferenceUploader {

    private static final String url = "http://localhost:8080/api/admin/upload-dictionary-item?groupTitle=LEON";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient httpClient;

    public DefaultWordReferenceUploader() {
        httpClient = new OkHttpClient();
    }

    @SneakyThrows
    public void uploadWordReferences(WordReference wordReference) {

        JSONObject object = new JSONObject(wordReference);
        String strBody = object.toString();
        RequestBody body = RequestBody.create(strBody, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("ERROR: " + call.request().body());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() > 199 && response.code() < 300) {
                    String string = Objects.requireNonNull(response.body()).string();
                    System.out.println(string);
                }
                response.close();
            }
        });
    }
}