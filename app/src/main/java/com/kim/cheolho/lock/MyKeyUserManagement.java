package com.kim.cheolho.lock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MyKeyUserManagement extends AppCompatActivity {
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_key_user_management);

        Intent intent = getIntent();
        String json = intent.getStringExtra("ToMyKeyUserManagement");


        gson = new Gson();
        JsonParser jsonParser1 = new JsonParser();
        JsonElement jsonElement1 = jsonParser1.parse(json).getAsJsonObject();

        //TODO: 새로운 DTO

        // DTO = gson.fromJson(jsonElement, DTO.class);

        ArrayList<String> list = new ArrayList<>();
//        list.add()

    }
}
